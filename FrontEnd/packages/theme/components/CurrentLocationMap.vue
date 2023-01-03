<template>
  <div>
    <div id="map"></div>
  </div>
</template>
<script>
export default {
  data: () => ({
    mapCenter: {
      lat: '',
      lag: ''
    },
    map: null,
    zoom: 14,
    marker: null
  }),
  created() {
    // this.service = new window.google.maps.places.AutocompleteService();
    // this.geocodeService = new window.google.maps.Geocoder();
  },

  mounted() {
    this.enableLocation();
  },

  methods: {
    // current location of user autodetect
    enableLocation() {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          (this.mapCenter.lat = position.coords.latitude),
            (this.mapCenter.lag = position.coords.longitude),
            this.setMap();
          this.myMarker();

          this.codeLatLng(this.mapCenter.lat, this.mapCenter.lag);
        },
        (error) => {
          console.log(error.message);
        }
      );
    },
    setMap() {
      this.map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        zoom: this.zoom
      });
    },
    myMarker() {
      this.marker = new google.maps.Marker({
        position: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        map: this.map,
        draggable: true
      });

      this.markerpos();
    },
    markerpos() {
      const that = this;
      google.maps.event.addListener(this.marker, 'dragstart', function(evt) {
        that.mapCenter.lat = evt.latLng.lat().toFixed(3);
        that.mapCenter.lag = evt.latLng.lng().toFixed(3);
      });
      google.maps.event.addListener(this.marker, 'dragend', function(evt) {
        that.mapCenter.lat = evt.latLng.lat().toFixed(3);
        that.mapCenter.lag = evt.latLng.lng().toFixed(3);
        that.codeLatLng(that.mapCenter.lat, that.mapCenter.lag);
      });
    },
    codeLatLng(lat, lng) {
      const geocoder = new google.maps.Geocoder();
      geocoder.geocode(
        { latLng: new google.maps.LatLng(lat, lng) },
        (results, status) => {
          if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
              //formatted address
              //this.location = results[0].formatted_address;
              console.log(results[0].formatted_address);
              this.$emit(
                'Currentlocation',
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
  name: 'CurrentLocationMap'
};
</script>
<style lang="scss" scoped>
div#map {
  height: 500px;
  width: 100%;
  overflow: hidden;
}
</style>
