<template>
  <div >
    <template>
      <div>
        <div class="provider-head aline-center side-padding">
          <div class="flexy">
            <img
              class="provide-img"
              :src="_pImage"
              alt=""
              :width="37"
              :height="39"
            />
            <div class="text-padding">
              <div class="aline-center">
                <div class="p-name">
                  {{_pName}}
                </div>
              </div>
              <span class="flexy">
                <span class="rating-css">
                  {{providerGetters.getProviderDistance(provider)}} min away
                </span>
              </span> 
            </div>
          </div>
          <div class="s-p-price" >
            ₹ {{_pPrice}}
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
          <SfButton  :class="{[_value]:Boolean(_value) ? '' : 'is-disabled--button'}" @click="toggleLocationDrop" id="btn">
            Confirm & Proceed</SfButton>
        </div>
    </template>
  </div>
</template>

<script>
import { SfImage, SfIcon, SfButton, SfSearchBar } from '@storefront-ui/vue';
//import AddToCart from './AddToCart.vue';
import { productGetters , providerGetters} from '@vue-storefront/beckn';
import { ref, computed } from '@vue/composition-api';

export default {
  name: 'ProductCard',
  components: {
    SfSearchBar,
    SfButton,
    SfImage,
   // AddToCart,
    SfIcon
  },
  props: {
    product: { type: Object },
    pName: { type: String, default: '' },
    pWieght: { type: String, default: '' },
    pPrice: { type: Number, default: '' },
    pImage: { type: String, default: '' },
    pCount: { type: Number, default: 0 },
  },

  setup(props, { emit }) {
    const _pName = computed(() => props.pName);
    const _pWieght = computed(() => props.pWieght);
    const _pPrice = computed(() => props.pPrice);
    const _pImage = computed(() => props.pImage);
    const _pCount = computed(() => props.pCount);
    const _SourceLocation=ref(JSON.parse(localStorage.getItem("slocation")));
    return {
      _SourceLocation,
      productGetters,
      providerGetters,
      _pName,
      _pWieght,
      _pPrice,
      _pImage,
      _pCount,
    };
  },
  
};
</script>
<style lang="scss" scoped>
.search-bar{
  padding-top: 0px;
  padding-bottom: 0px;
  padding-left: 0px;
  padding-right: 0px;
}
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
  img{
    border-radius: 9px;
  }
  /*.sf-search-bar{
    left: ;
  }*/
</style>
