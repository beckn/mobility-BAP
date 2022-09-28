<template>
    <div>
    <div id="cafe-map"></div>
    <div>
      <div id="location-btn">
        <div>
          <div class="popover-bg">
            <div class="popover-content position-relative">
              <!-- <div v-if="bName === 'selectcab'">
                <Select />
              </div> -->
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
    import { ref, computed } from '@vue/composition-api';
    import Select from '../pages/select.vue';
    import DriverInfo from '../pages/DriverInfo.vue';
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
        //this.getLocationDetails(JSON.parse(Slocaltorage.getItem('SourceLocation')));
        //this.getLocationDetails()
        // this.service = new window.google.maps.places.AutocompleteService();
        // this.geocodeService = new window.google.maps.Geocoder();
      },
      mounted() {
       //this.getlocation()
        //this.$refs.locationAutocomplete.focus();
        this.getLocationDetails(JSON.parse(Slocaltorage.getItem('SourceLocation')));
      },
      methods: {
        reload() {
          window.location.reload();
        },
        // displaySuggestions(predictions, status) {
        //   if (status !== window.google.maps.places.PlacesServiceStatus.OK) {
        //     this.searchResults = [];
        //     return;
        //   }
        //   this.searchResults = predictions;
        // },
    
        getLocationDetails(selectedLocation) {
          this.location = selectedLocation.description;
          this.geocodeService
            .geocode({ placeId: selectedLocation.place_id })
            .then((response) => {
            //   this.$emit(
            //     'locationSelected',
            //     response.results[0].geometry.location.lat(),
            //     response.results[0].geometry.location.lng(),
            //     selectedLocation.description
            //   );
              //this.show = !this.show;
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
      name: 'Trip has Started',
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