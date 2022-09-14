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
          <SfButton class="button-pos sf-button--pure pos-left">
            <span class="sf-search-bar__icon">
              <SfIcon color="var(--c-text)" size="10px" icon="marker" />
            </span>
          </SfButton>
          {{ result.structured_formatting.main_text }}
          <p>{{ result.structured_formatting.secondary_text }}</p>
        </li>
      </ul>

      <div v-if="visible" class="btn">
        <SfButton id="btn" @click="enableLocation()"
          ><h5>Current-Location</h5></SfButton
        >
      </div>
      <template>
        <div>
          <div id="taxi-map"></div>
          <div v-if="!show">
            <div id="location-btn">
              <div>
                <div class="popover-bg">
                  <div class="popover-content position-relative">
                    <br />
                    <div>
                      <h4>Set Location</h4>
                      <div
                        class="close"
                        @click="$emit('edit')"
                      ></div>
                    </div>

                    <hr style="width:100%;" />
                    <h6 style="font-weight:400; padding: 0%;">
                      <p>Location</p>
                      {{ this.location }}
                    </h6>
                    <hr style="width:100%;" />

                    <SfButton @click="$emit('toggleLocationDrop')" id="btn1">
                      Set Location</SfButton
                    >

                    <!-- <ModalComponent class="modalclass" /> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </slot>
  </div>
</template>

<script>
import { SfButton, SfIcon } from '@storefront-ui/vue';

export default {
  props: ['geolocation', 'buttonlocation'],
  data: () => ({
    location: '',
    visible: false,

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
  }),

  created() {
    this.service = new window.google.maps.places.AutocompleteService();
    this.geocodeService = new window.google.maps.Geocoder();
    if (this.geolocation === false) {
      this.enableLocation();
    }

    if (this.buttonlocation === true) {
      this.change();
    }
  },

  mounted() {
    this.$refs.locationAutocomplete.focus();
  },

  methods: {
    change() {
      this.visible = true;
    },
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
      localStorage.setItem('SourceLocation', JSON.stringify(selectedLocation));
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

          // this.getitem= localStorage.getItem('lat')

          this.setMap();
          this.visible = false;

          // eslint-disable-next-line no-alert
        })
        .catch((err) => alert(err));
    },
    // gettting google map
    setMap() {
      this.map = new google.maps.Map(document.getElementById('taxi-map'), {
        center: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        zoom: this.zoom
      });
      this.marker = new google.maps.Marker({
        position: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        map: this.map
      });
      // console.log(this.getitem);
    },
    // current location of user autodetect
    enableLocation() {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          (this.mapCenter.lat = position.coords.latitude),
            (this.mapCenter.lag = position.coords.longitude),
            this.setMap();
          this.show = !this.show;
          this.codeLatLng(this.mapCenter.lat, this.mapCenter.lag);
        },
        (error) => {
          console.log(error.message);
        }
      );
      this.visible = false;
    },
    // current user location city name
    codeLatLng(lat, lng) {
      const geocoder = new google.maps.Geocoder();
      geocoder.geocode(
        { latLng: new google.maps.LatLng(lat, lng) },
        (results, status) => {
          if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
              //formatted address
              this.location = results[0].formatted_address;

              this.$emit(
                'locationSelected',
                lat,
                lng,
                results[0].formatted_address
              );
            } else {
              alert('No results found');
            }
          } else {
            alert('Geocoder failed due to: ' + status);
          }
        }
      );
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
      }
    }
  },

  name: 'LocationSearchBar',
  components: {
    SfButton,
    SfIcon
  }
};
</script>
<style lang="scss" scoped>
.button-pos {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 20px
}
.sf-search-bar__icon {
  padding-left: 70%;
  
}
div#taxi-map {
  width: 100%;
  height: 450px;
  overflow: hidden;
}
#btn1 {
  width: 100%;
  margin-bottom: 0%;
}

#location-btn {
  height: 50px;
  border-top-left-radius: 25px;
  border-top-right-radius: 25px;
  box-shadow: rgba(50, 50, 50, 0.75);
  overflow: visible;
  // height: 10%;
  padding-bottom: 0%;
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
.btn {
  display: flex;
  align-items: center;
  justify-content: center;
}
#btn {
  border-radius: 4px;
  width: 156px;
  height: 22px;
  margin-bottom: 0%;
}
h5 {
  font-family: 'SF Pro Text';
  font-style: normal;
  font-weight: 600;
  font-size: 12px;
  line-height: 22px;
  padding: 4px;
}
</style>
