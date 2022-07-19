<template>
  <div>
    <template>
        <div>
              <div class="provider-head aline-center side-padding">
                <div class="flexy">
                  <img
                    class="provide-img"
                    src="https://icon-icons.com/icon/car/126245"
                    alt=""
                    :width="35"
                    :height="36"
                  />
                  <div class="text-padding">
                    <div class="aline-center">
                      <div class="p-name">
                          ola Mini
                      </div>
                      <!--<div class="text-padding">
                        <span class="p-distance">by</span>
                        <span>{{
                          providerGetters.getProviderBpp(bpp.bpp_descriptor)
                        }}</span>
                      </div>-->
                    </div>
                    <span class="flexy">
                      <!--<div class="p-distance">
                        {{providerGetters.getProviderDistance(provider)}} min away
                      </div> -->
                      <span class="rating-css">
                        5 min away
                      </span>
                    </span> 
                  </div>
                </div>
                <div class="s-p-price" >
                  <!--<div class="s-p-price" v-if="_updatedCount !== 0">
                    ₹ {{ _updatedPrice ? _updatedPrice : _pPrice }}
                  </div>-->
                  ₹ 80
                </div>    
              </div>
                
              <div><hr class="sf-divider" /></div>
              <div>
                <select class="form-select">
                  <option selected>Ride Now</option>
                  <option value="Schedule">Schedule</option>
                  <option value="Recurring">Recurring</option>
                </select>
              </div>

              <div class="flexy">   
                <SfIcon class="locationicon" color="#f37a20" size="20px" icon="marker" /> 
                  <div class="inputs-container location-block">
                    <span>source</span>
                    <div class="search-bar side-padding">
                      <SfSearchBar
                        placeholder="Enter Source"
                        aria-label="Search"
                        :icon="null"  
                        :value="_SourceLocation"                      
                      >
                      </SfSearchBar>
                    </div>
                  </div>
              </div>

              <div><hr class="sf-divider" /></div>
                <div class="flexy">
                  <SfIcon color="#2081F3" class="locationicon" size="xxl" icon="marker" />                 
                  <div class="inputs-container location-block">
                    <span>Destination</span>
                    <div class="search-bar side-padding">
                      <SfSearchBar
                        placeholder="Enter Destination"
                        aria-label="Search"
                        :icon="null" 
                                              
                      >
                      </SfSearchBar>
                    </div>
                  </div>                 
                </div>
                <div><hr class="sf-divider" /></div>
                <!--<div v-if="show==true">-->
                <SfButton  :class="{[_value]:Boolean(_value) ? '' : 'is-disabled--button'}" @click="toggleLocationDrop" id="btn">
                  Confirm & Proceed</SfButton
                >
                <!--</div>-->
                <!--<div v-if="show==false">
                <SfButton class="is-disabled--button" @click="$emit('toggleLocationDrop')" id="btn">
                  Confirm & Proceed</SfButton
                >
                </div>-->
                <!-- <ModalComponent class="modalclass" /> -->
        </div>
    </template>
  </div>
</template>

<script>
import {
  SfSidebar,
  SfSearchBar,
  SfHeading,
  SfButton,
  SfIcon,
  SfProperty,
  SfPrice,
  SfCollectedProduct,
  SfImage,
  SfInput
} from '@storefront-ui/vue';
import {
  productGetters,
  providerGetters,
  cartGetters,
  useCart,
  useFacet,
  useSearch
} from '@vue-storefront/beckn';
import Location from '../components/Location.vue'
import { ref, onBeforeMount, watch } from '@vue/composition-api';
import { useUiState } from '~/composables';

export default {
    components: {
        productGetters,
        providerGetters,
        cartGetters,
        useCart,
        useFacet,
        useSearch,
        Location,
        SfSearchBar,
        SfSidebar,
        SfHeading,
        SfButton,
        SfIcon,
        SfProperty,
        SfPrice,
        SfCollectedProduct,
        SfImage,
        SfInput
    },
    computed: {
      isLocationSelected() {
        return this.location !== '';
      },
    },
    setup(_, { root }) {
        const { selectedLocation, updateLocation } = useUiState();
        const location = ref(selectedLocation?.value?.address);
        const show=ref(false);
        const _value=ref(true);
        //const _DestLocation=ref(JSON.parse(localStorage.getItem("dlocation")));
        const _SourceLocation=ref(JSON.parse(localStorage.getItem("slocation")));
        const isLocationdropOpen = ref(false);
        const { toggleSearchVisible } = useUiState();
        const toggleLocationDrop = () => {
          isLocationdropOpen.value = !isLocationdropOpen.value;
          console.log("hello");
        };
        const goBack = () => {
        root.$router.back();
        toggleSearchVisible(true);
        };
        const locationSelected = (latitude, longitude, address) => {
          location.value = address;
          // toggleLocationDrop();
          updateLocation({
            latitude: latitude,
            longitude: longitude,
            address: address
          });
        };
        return{
            isLocationdropOpen,
            toggleLocationDrop,
            locationSelected,
            goBack,
            _value,
            show,
            _SourceLocation,
           // _DestLocation,
        }
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
    .icon_back {
      position: absolute;
      left: 0;
      margin: 10px;
    }
  }

  .inputs-container {
    margin-bottom: 28px;
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
  div#cafe-map {
    width: 100%;
    height: 500px;
    position: fixed;
  }
  /*.location-btn{
      border-radius: 20px;
      width: 100%;
      height: 500px;//363px;
      //top: 367px;//397px
      //position: fixed;
      box-shadow: 0px -5px 40px rgba(0, 0, 0, 0.1);
      //background-color: pink;
  }*/
  #btn {
    //top:112px;
    width: 63px;
    box-shadow: 0px -5px 40px rgba(0, 0, 0, 0.15);
    width: 100%;
    margin-bottom: 0%;
  }
  .locationicon{
    left: 10%;
    width: 30px;
    height: 30px;
    margin-right: 20px;
  }
  .form-select{
      width: 80%;
      height: 36px;
      padding: 5px 5px;
      color: #f37a20;
      margin: 10%;
      background: #FFFFFF;
      border: 1px solid #E6E6E6;
      border-radius: 4px;
  }
  .s-p-price{
    font-family: 'Roboto';
    font-style: normal;
    font-weight: 700;
    font-size: 16px;
    line-height: 19px;
    position: absolute; 
    right: 10%; 
    width: 100px; 
    text-align:right;
    color: #f37a20;
  }
  .loc-input{
    font-size: 12px;
    height: 0px;
    
  }
  .location-block{
    margin-left: 25px;
  }
  .display-map{
    height: 250px;
    width: 100%;
    background-color: antiquewhite;
  }
  
</style>