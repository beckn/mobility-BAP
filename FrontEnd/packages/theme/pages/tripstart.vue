<template>
  <div>
    <div class="top-bar header-top">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div v-if="isFulfillmentAvailable">{{ tripStatusVal }}</div>
    </div>
    <div id="cafe-map"></div>
    <div>
      <div id="location-btn">
        <div>
          <div class="popover-bg">
            <div class="popover-content position-relative">
              <div>
                <DriverInfo :DriverInfo="DriverInfo" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { SfButton, SfIcon } from '@storefront-ui/vue';
import DriverInfo from '../pages/DriverInfo.vue';
import { ref, watch, onBeforeMount, computed } from '@vue/composition-api';
import { useOrderStatus, useTrack } from '@vue-storefront/beckn';
import superAgent from 'superagent';

export default {
  data: () => ({
    service: null,
    geocodeService: null,
    map: null,
    marker: null,
    SourceLocation: '',
    destloc: '',
    marker: null,
    intervalid1: ''
  }),

  created() {
    this.service = new window.google.maps.places.AutocompleteService();
    this.geocodeService = new window.google.maps.Geocoder();
  },

  mounted() {
    this.SourceLocation = JSON.parse(localStorage.getItem('slocation'));
    this.destloc = JSON.parse(localStorage.getItem('destinationLocation'));

    this.getlocation();
    this.driverposition();
  },
  beforeDestroy() {
    clearInterval(this.intervalid1);
  },

  methods: {
    driverposition() {
      this.intervalid1 = setInterval(() => {
        this.markers();
      }, 2000);
    },
    calculateAndDisplayRoute(start, end, map) {
      const directionsService = new google.maps.DirectionsService();
      const directionsRenderer = new google.maps.DirectionsRenderer();
      directionsRenderer.setOptions({
        polylineOptions: {
          strokeColor: 'black'
        }
      });
      directionsRenderer.setMap(map);
      directionsService
        .route({
          origin: start,
          destination: end,
          travelMode: 'DRIVING'
        })
        .then((response, status) => {
          directionsRenderer.setDirections(response);
        })
        .catch((e) => {
          console.error(e);
          window.alert('Directions request failed due to ' + e.message);
        });
    },
    getlocation() {
      const start = new google.maps.LatLng(18.5204, 73.8567);
      this.map = new google.maps.Map(document.getElementById('cafe-map'), {
        center: start,
        zoom: 12,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      });
      //this.markers();

      this.calculateAndDisplayRoute(
        this.SourceLocation,
        this.destloc,
        this.map
      );
    },
    markers() {
      const movingIcon = new google.maps.MarkerImage('/icons/yellowcar.png');
      this.marker = new google.maps.Marker({
        //varible of markers lat and long are hardcoded .
        position: {
          lat: localStorage.getItem('trackLat')
            ? parseFloat(localStorage.getItem('trackLat'))
            : 0,

          lng: localStorage.getItem('trackLong')
            ? parseFloat(localStorage.getItem('trackLong'))
            : 0
        },
        map: this.map,
        icon: movingIcon,
        

      });
    }
  },

  name: 'TripStart',
  components: {
    SfButton,
    SfIcon,
    DriverInfo
  },

  setup(_, { root }) {
    const goBack = () => {
      root.$router.back();
    };
    const DriverInfo = ref(false);
    const tripStatusVal = ref('Ride is Confirmed');

    const {
      poll: onStatus,
      init: status,
      pollResults: statusResults,
      stopPolling: stopStatuspolling
    } = useOrderStatus('status');

    const {
      init: track,
      poll: onTrack,
      pollResults: trackResults,
      stopPolling: onTrackStopPolling
    } = useTrack('track');

    const isFulfillmentAvailable = computed(() => {
      if (statusResults.value !== null) {
        if (statusResults.value[0].message) {
          DriverInfo.value = true;
          tripStatusVal.value = statusResults.value[0].message.order.state;
        }
      }
      return statusResults.value;
    });

    const transactionId = localStorage.getItem('transactionId');
    const bpp_id = JSON.parse(localStorage.getItem('cartItem')).bpp_id;
    const bpp_uri = JSON.parse(localStorage.getItem('cartItem')).bpp_uri;
    const orderID = JSON.parse(localStorage.getItem('confirmData')).order.id;

    const lat = ref(12.9732);
    const long = ref(77.6089);

    const tripStatus = async () => {
      const params = [
        {
          context: {
            // eslint-disable-next-line camelcase
            transaction_id: transactionId,
            // eslint-disable-next-line camelcase
            bpp_id: bpp_id,
            bpp_uri: bpp_uri
          },
          message: {
            // eslint-disable-next-line camelcase
            order_id: orderID
          }
        }
      ];
      const response = await status(params, localStorage.getItem('token'));
      await onStatus({ orderIds: orderID }, localStorage.getItem('token'));
    };

    const tripTrack = async () => {
      const formattedInitResult = JSON.parse(
        localStorage.getItem('initResult')
      );
      const params = [
        {
          context: JSON.parse(localStorage.getItem('confirmDataContext')),
          message: {
            order_id: formattedInitResult[0].message.order.id
          }
        }
      ];
      try {
        const response = await track(params, localStorage.getItem('token'));
        await onTrack(
          { messageIds: response[0].context.message_id },
          localStorage.getItem('token')
        );
      } catch (error) {
        console.error(error);
      }
    };

    onBeforeMount(async () => {
      await tripStatus();
      await tripTrack();
    });

    watch(
      () => trackResults.value,

      async (trackResult) => {
        if (trackResult) {
          if (trackResult[0].message) {
            if (trackResult[0].message.tracking) {
              try {
                const res = await superAgent.get(
                  trackResults.value[0].message.tracking.url
                );

                const coordinatesArray = res.body.gps.split(',');

                lat.value = coordinatesArray[0];
                long.value = coordinatesArray[1];

                localStorage.setItem('trackLat', lat.value);
                localStorage.setItem('trackLong', long.value);
              } catch (error) {
                console.error('location error', error);
              }
            }
          }
        }
      }
    );

    return {
      lat,
      long,
      goBack,
      tripStatus,
      tripStatusVal,
      DriverInfo,
      isFulfillmentAvailable
    };
  }
};
</script>

<style lang="scss" scoped>
.top-bar {
  align-items: center;
  display: flex;
  font-size: 18px;
  justify-content: space-around;
  height: 60px;
  font-weight: 500;
  background: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.07);
}

.icon_back {
  position: absolute;
  left: 0;
  margin: 10px;
}

.button-pos {
  position: absolute;
  right: 0;
  top: 0;
}

.sf-search-bar__icon {
  padding-left: 80%;
}

div#cafe-map {
  width: 100%;
  //height: 280px;
  height: 500px;
  position: fixed;
}

#btn {
  width: 100%;
  margin-bottom: 0%;
}

#location-btn {
  border-top-left-radius: 25px;
  border-top-right-radius: 25px;
  box-shadow: rgba(50, 50, 50, 0.75);
  height: 150px;
}

.close {
  position: absolute;
  right: 32px;
  top: 32px;
  width: 32px;
  height: 32px;
  opacity: 1;
}

.close:hover {
  opacity: 1;
}

.close:before,
.close:after {
  position: absolute;
  left: 15px;
  content: ' ';
  height: 33px;
  width: 2px;
  background-color: #333;
}

.close:before {
  transform: rotate(45deg);
}

.close:after {
  transform: rotate(-45deg);
}
</style>
