<template>
  <div style="margin: 10px;">
    <div class="top-bar header-top">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div>
        Geofence
      </div>
    </div>
    <div id="map"></div>
    <div style=" margin: 8px;">
      <SfButton
        id="btn2"
        class="button-pos sf-button--pure color-primary"
        @click="goHome"
        v-e2e="'home-search-button'"
        ><label for="btn">Go Back to Home</label>
      </SfButton>
    </div>
  </div>
</template>
<script>
import { SfButton, SfIcon } from '@storefront-ui/vue';
import { ref, onMounted } from '@vue/composition-api';

export default {
  components: {
    SfButton,
    SfIcon
  },

  data: () => ({
    mapCenter: {
      lat: 51.173791,
      lag: 9.327271
    },
    zoom: 15,

    polygoneCoords: [
      { lat:'', lng:''},
      { lat: '', lng: '' },
      { lat: '', lng: ''},
      { lat: '', lng: ''},
      { lat: '', lng: ''}
    ]
  }),

  mounted() {
    
    const polygon = sessionStorage.getItem('poligon').split(',');
    console.log(polygon);
    this.mapCenter.lat=parseFloat(polygon[0]);
    this.mapCenter.lag= parseFloat(polygon[1]); 
    this.polygoneCoords[0].lat = parseFloat(polygon[0]);
    this.polygoneCoords[0].lng = parseFloat(polygon[1]);
    this.polygoneCoords[1].lat = parseFloat(polygon[2]);
    this.polygoneCoords[1].lng = parseFloat(polygon[3]);
    this.polygoneCoords[2].lat = parseFloat(polygon[4]);
    this.polygoneCoords[2].lng = parseFloat(polygon[5]);
    this.polygoneCoords[3].lat = parseFloat(polygon[6]);
    this.polygoneCoords[3].lng = parseFloat(polygon[7]);
    this.polygoneCoords[4].lat = parseFloat(polygon[8]);
    this.polygoneCoords[4].lng = parseFloat(polygon[9]);
    console.log(this.polygoneCoords);
    this.Map();
  },
  methods: {
    Map() {
      const myPolygon = new google.maps.Polygon({
        paths: this.polygoneCoords,
        strokeColor: 'rgba(0, 0, 0, 1)',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: 'rgba(243, 122, 32, 0.4)',
        fillOpacity: 0.35
      });

      const map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: this.mapCenter.lat, lng: this.mapCenter.lag },
        zoom: this.zoom
      });
      myPolygon.setMap(map);
    }
  },
  watch: {
    //   upadateMap: function(newVal, oldVal) {
    //     // watch it
    //     console.log('N:',newVal, 'O:',oldVal)
    //   }
  },
  name: 'Geofence',
  setup(_, context) {
    const goBack = () => {
      context.root.$router.back();
    };

    const goHome = () => {
      context.root.$router.push('/');
    };

    return {
      goBack,
      goHome
    };
  }
};
</script>
<style lang="scss" scoped>
div#map {
  @media (max-height: 667px) {
    height: 300px;
  }
  height: 550px;
  width: 100%;
  overflow: hidden;
}
.top-bar {
  padding-right: 35%;
  padding-left: 10px;
  align-items: center;
  display: flex;
  font-size: 18px;
  justify-content: space-between;
  height: 60px;
  font-weight: 500;
  background: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.07);
}
#btn2 {
  width: 328px;
  height: 48px;
  background: #f37a20;
  border-radius: 4px;
  width: 100%;
  margin-top: 5%;

  label {
    font-weight: 600;
    letter-spacing: 0.8px;
    font-size: 17px;
    color: aliceblue;
  }
}
</style>
