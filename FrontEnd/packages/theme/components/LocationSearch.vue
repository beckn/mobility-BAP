<template>
  <div>
    <template>
      <div id="cafe-map"></div>
      <div>
        <div id="location-btn">
          <div>
            <div class="popover-bg">
              <div class="popover-content position-relative">
                <div v-if="bName === 'selectcab'">
                  <Select />
                </div>
                <div v-else-if="bName === 'confirmride'">
                  <DriverInfo />
                </div>
                <!--<Selectcab/>
                <!- <ModalComponent class="modalclass" /> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { SfButton, SfIcon } from '@storefront-ui/vue';
import { ref, computed } from '@vue/composition-api';
import Select from '../pages/select.vue';
import DriverInfo from '../pages/DriverInfo.vue';
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
    // reload() {
    //   window.location.reload();
    // },
    // displaySuggestions(predictions, status) {
    //   if (status !== window.google.maps.places.PlacesServiceStatus.OK) {
    //     this.searchResults = [];
    //     return;
    //   }
    //   this.searchResults = predictions;
    // },
    //    myfunction(){

    //     this.start = new google.maps.LatLng(7.434876909631617,80.4424951234613);
    //     this.end = new google.maps.LatLng(7.3178281209262686,80.8735878891028);
    //       // const option ={
    //       //     zoom : 10,
    //       //     center : start
    //       // };
    //       this.map = new google.maps.Map(document.getElementById('cafe-map'),this.option);
    //       this.display = new google.maps.DirectionsRenderer();
    //       this.services = new google.maps.DirectionsService();
    //       display.setMap(this.map);
    //           this.request = {
    //               origin : this.start,
    //               destination:this.end,
    //               travelMode: 'DRIVING'
    //           };
    //           services.route(request,function(result,status){
    //             //const that=this;
    //               if(status =='OK'){
    //                 display.setDirections(result);
    //               }
    //           });
    //   },
    // // },

    // getLocationDetails(selectedLocation) {
    //   this.location = selectedLocation.description;
    //   this.geocodeService
    //     .geocode({ placeId: selectedLocation.place_id })
    //     .then((response) => {
    //       this.$emit(
    //         'locationSelected',
    //         response.results[0].geometry.location.lat(),
    //         response.results[0].geometry.location.lng(),
    //         selectedLocation.description
    //       );
    //       this.show = !this.show;
    //       this.mapCenter.lat = response.results[0].geometry.location.lat();
    //       this.mapCenter.lag = response.results[0].geometry.location.lng();
    //       this.getlocation();

    //       // eslint-disable-next-line no-alert
    //     })
    //     .catch((err) => alert(err));
    // },

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

  name: 'LocationSearch',
  components: {
    SfButton,
    SfIcon,
    Select,
    DriverInfo
  },
  props: {
    b_name: { type: String, default: '' }
  },
  setup(props) {
    const bName = computed(() => props.b_name);

    return {
      bName
    };
  }
};
</script>
<style lang="scss" scoped>
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
