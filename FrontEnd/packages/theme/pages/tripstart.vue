<template>
  <div>
    <slot name="locationInput">
      <div class="position-relative">
        <div v-if="show">
          <input
            ref="locationAutocomplete"
            v-model="location"
            type="text"
            placeholder="Enter Location"
            aria-label="Select Location"
            class="
            sf-header__search
            be-search-location
            sf-search-bar
            sf-header__search
            be-search-location
          "
            v-e2e="'app-location-sidebar-input'"
          />
          <SfButton class="button-pos sf-button--pure">
            <span class="sf-search-bar__icon">
              <SfIcon color="var(--c-text)" size="18px" icon="search" />
            </span>
          </SfButton>
        </div>
      </div>
      <ul class="location-list" v-if="show">
        <li
          v-for="(result, i) in searchResults"
          :key="i"
          @click="getLocationDetails(result)"
          v-e2e="'app-location-sidebar-input-options'"
        >
          <SfButton class="button-pos sf-button--pure">
            <span class="sf-search-bar__icon">
              <SfIcon color="var(--c-text)" size="10px" icon="marker" />
            </span>
          </SfButton>
          {{ result.structured_formatting.main_text }}
          <p>{{ result.structured_formatting.secondary_text }}</p>
        </li>
        <!-- <p> lat:{{`${this.mapCenter.lat} and lag ${this.mapCenter.lag}`}}  </p> -->
      </ul>
    </slot>
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
                <DriverInfo />
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
    location: '',
    searchResults: [],
    service: null,
    geocodeService: null,
    mapCenter: {
      lat: '',
      lag: ''
    },
    map: null,
    zoom: 14,
    show: true,
    marker: null

    // map:{lg:this.mapCenter.lag,lt:this.mapCenter.lat,}
    // mapCen}ter:{lag:this.log,lag:this.log}
  }),
  created() {
    this.service = new window.google.maps.places.AutocompleteService();
    this.geocodeService = new window.google.maps.Geocoder();
  },
  mounted() {
    //this.$refs.locationAutocomplete.focus();
    this.getLocationDetails(JSON.parse(localStorage.getItem('SourceLocation')));
  },
  methods: {
    reload() {
      window.location.reload();
    },
    displaySuggestions(predictions, status) {
      if (status !== window.google.maps.places.PlacesServiceStatus.OK) {
        this.searchResults = [];
        return;
      }
      this.searchResults = predictions;
    },

    getLocationDetails(selectedLocation) {
      this.location = selectedLocation.description;
      this.geocodeService
        .geocode({ placeId: selectedLocation.place_id })
        .then((response) => {
          this.$emit(
            'locationSelected',
            response.results[0].geometry.location.lat(),
            response.results[0].geometry.location.lng(),
            selectedLocation.description
          );
          this.show = !this.show;
          this.mapCenter.lat = response.results[0].geometry.location.lat();
          this.mapCenter.lag = response.results[0].geometry.location.lng();
          this.getlocation();

          // eslint-disable-next-line no-alert
        })
        .catch((err) => alert(err));
    },
    getlocation() {
      this.map = new google.maps.Map(document.getElementById('cafe-map'), {
        center: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        zoom: this.zoom
      });
      this.marker = new google.maps.Marker({
        position: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        map: this.map
      });
    }
  },
  watch: {
    location(newValue) {
      if (newValue) {
        this.service.getPlacePredictions(
          {
            input: this.location,
            types: ['geocode']
          },
          this.displaySuggestions
        );
        //localStorage.setItem('slocation', JSON.stringify(this.location));
      }
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
        watch(
        () => pollResults.value,
        (newValue) => {
          if(newValue[0].message.order.state){
            stopPolling();
            setTimeout(() => {
              root.$router.push('/orderSuccess');
            }, 10000);                        
          }
          // if(newValue[0].message.order.state === "Ended"){
          //   stopPolling();
          //   root.$router.push('/orderSuccess');
          // }          
          // else if(newValue[0].message.order.state){
          //   tripStatusVal.value=newValue[0].message.order.state;          
          // }
        }
      );
    }
    onBeforeMount(async () => {
      //enableLoader.value = true;     
      await tripStatus();
      //enableLoader.value = false;
    });
    return {
      goBack,
      tripStatus,
      tripStatusVal
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
  height: 400px;
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