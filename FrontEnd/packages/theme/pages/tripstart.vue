<template>
  <div>
  
    <div class="top-bar header-top">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div>{{ tripStatusVal }}</div>
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
              <!--<Selectcab/>
              <!- <ModalComponent class="modalclass" /> -->
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
import { ref, watch, onBeforeMount } from '@vue/composition-api';
import {
  useOrderStatus
} from '@vue-storefront/beckn';

export default {
    data: () => ({
    service: null,
    geocodeService: null,
    map: null,
    marker: null,
    SourceLocation: '',
    destloc: ''
  }),
  created() {
    this.service = new window.google.maps.places.AutocompleteService();
    this.geocodeService = new window.google.maps.Geocoder();
  },
  mounted() {
    this.SourceLocation = JSON.parse(localStorage.getItem('slocation'));
    this.destloc = JSON.parse(localStorage.getItem('destinationLocation'));
    this.getlocation();
  },
  methods: {
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
        .catch((e) =>
          window.alert('Directions request failed due to ' + status)
        );
    },
    getlocation() {
      const start = new google.maps.LatLng(18.5204, 73.8567);
      this.map = new google.maps.Map(document.getElementById('cafe-map'), {
        center: start,
        zoom: 14,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      });
      this.calculateAndDisplayRoute(
        this.SourceLocation,
        this.destloc,
        this.map
      );
    },
    marker() {
      this.marker = new google.maps.Marker({
        position: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        map: this.map
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
    const tripStatusVal = ref("Awaiting Driver acceptance") 
    const {
      poll,
      init,
      pollResults,
      stopPolling
    } = useOrderStatus('status');
    
    const transactionId = localStorage.getItem('transactionId');
    const bpp_id= JSON.parse(localStorage.getItem('cartItem')).bpp_id;
    const bpp_uri= JSON.parse(localStorage.getItem('cartItem')).bpp_uri;
    const orderID = JSON.parse(localStorage.getItem('confirmData')).order.id;
    const tripStatus = async () => {
      const params = [{
        context: {
          // eslint-disable-next-line camelcase
          transaction_id: transactionId,
          // eslint-disable-next-line camelcase
          bpp_id: bpp_id,
          bpp_uri:bpp_uri
        },
        message: {
          // eslint-disable-next-line camelcase
          order_id: orderID
        }
      }];
        const response = await init(params, localStorage.getItem('token'));
        await poll({ orderIds: orderID }, localStorage.getItem('token'));
        const displayStatus = (x) =>{
            setTimeout(() => {
                tripStatusVal.value = x;
              }, 10000);
            //   if(x==="Ride Ended"){
            //     setTimeout(() => {
            // //   root.$router.push('/orderSuccess');
            // // }, 10000);  
            //   }
          }  
        watch(
        () => pollResults.value,
        (newValue) => {
          if(newValue[0].message.order.state){
            stopPolling();               
            var tripStatusArr=["Driver has accepted the ride" ,"Driver is on the way","Ride Started","Ride Ended"];
            
           
            var index = 0;
            let displayStatus = setInterval(function() {
              tripStatusVal.value = tripStatusArr[index++ % tripStatusArr.length];

             DriverInfo.value=true
              if(tripStatusVal.value==="Ride Ended"){
                clearInterval(displayStatus);
                root.$router.push('/orderSuccess');
              }
            }, 8000);                    
          }
        }
      );
    }
    onBeforeMount(async () => {    
      await tripStatus();
    });
    return {
      goBack,
      tripStatus,
      tripStatusVal,
      DriverInfo
    };
  }
}

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