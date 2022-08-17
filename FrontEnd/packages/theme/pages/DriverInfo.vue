<template>
  <div >
    <!--<div class="top-bar">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div class="header-push">Ride is Confirmed</div>
    </div>-->

    <!--<div>
      <img src="/icons/beckn.png" class="mapdata"/>
    </div>-->
    <div class="layout-container driver-data">
      <div class="location-content">
        <client-only>
          <div class="location-icon">
            <slot>
                <div
                  @click="toggleIsShow"
                  class=""
                  v-e2e="'app-header-location-input-div'"
                >
                <template>
                    <div class="button-pos1">
                      <SfButton class="sf-button--pure">
                        <!--<span class="sf-search-bar__icon">-->
                          <SfImage
                            src="/icons/Rectangle-bar.png"
                            :width="60"
                            :height="5.5"
                            alt="Rectangle bar"
                          />
                        <!--</span>-->
                      </SfButton>
                    </div>
                
                    <div>
                      <div class="" >
                        <template>
                          <div>
                            <div class="provider-head aline-center side-padding">
                              <div class="flexy">
                                <div class="text-padding">
                                  <div class="aline-center">
                                    <div class="p-name">
                                      KA05 AF 6226
                                    </div>
                                  </div>
                                  <span class="flexy">
                                    <span class="rating-css">
                                    Toyota Etios, Silver
                                    </span>
                                  </span> 
                                </div>
                              </div>
                              <div class="button-pos" >
                                <div class="text-padding">
                                  <div class="aline-center">
                                    <div class="p-name text-color">
                                      6363
                                    </div>
                                  </div>
                                  <span class="flexy">
                                    <span class="">
                                      OTP
                                    </span>
                                  </span> 
                                </div>
                              </div>    
                            </div>
                                    
                            <div><hr class="sf-divider" /></div>

                            <div class="provider-head aline-center side-padding">
                              <div class="flexy">
                                <img
                                  class="provide-img"
                                  src="/icons/contactSupport.png"
                                  alt=""
                                  :width="37"
                                  :height="39"
                                />
                                <div class="text-padding">
                                  <div class="aline-center">
                                    <div class="p-name">
                                      Manjunath Reddy
                                    </div>
                                  </div>
                                  <span class="flexy">
                                    <div class="rating-css">
                                        4
                                    </div>
                                    <span class="sf-rating__icon">
                                        <SfIcon color="#FADB14" size="16px" icon="star" />
                                    </span>
                                  </span> 
                                </div>
                                        
                              </div>
                              <div class="button-pos" >
                                <SfButton class="contact">
                                    <img
                                        class="contact-img"
                                        id="icon"
                                        src="/icons/contact.png"
                                        alt="Vue Storefront Next"
                                    />
                                </SfButton>
                              </div>    
                            </div>

                            <div class="provider-head aline-center side-padding">
                              <div class="t-c-l-data">
                                Temperature  
                              </div>
                              <div class="button-pos t-c-l-data" >
                                97.3<sup><span>&#176;</span></sup>F
                              </div>    
                            </div>

                            <div class="provider-head aline-center side-padding">
                              <div class="t-c-l-data">
                                Last Checked at
                              </div>
                              <div class="button-pos t-c-l-data" >
                                12:34pm
                              </div>    
                            </div>

                            <div class="provider-head aline-center side-padding">
                              <div class="t-c-l-data">
                                Language Known
                              </div>
                              <div class="button-pos t-c-l-data" >
                                Hindi & Kannada
                              </div>    
                            </div>
                          </div>
                        </template>
                      </div>
                    </div>
                </template>
                </div>
            </slot>
          </div>
        </client-only>
          <div >
            <template>
              <!--<div v-if="!!isShow" >-->
                
                <BottomSlider :visible="isShow" @close="closeModal">

                  <template>
                    <div class="bar-pos"  @click="toggleIsShow">
                      <SfButton class="sf-button--pure">
                        <SfImage
                          src="/icons/Rectangle-bar.png"
                          :width="60"
                          :height="5.5"
                          alt="Rectangle bar"
                        />
                        </SfButton>
                    </div>
                    
                        <div>
                          <div>
                            <template>
                              <div>
                                <div class="provider-head aline-center side-padding">
                                  <div class="flexy">
                                    <div class="text-padding">
                                      <div class="aline-center">
                                        <div class="p-name">
                                          KA05 AF 6226
                                        </div>
                                      </div>
                                      <span class="flexy">
                                        <span class="rating-css">
                                        Toyota Etios, Silver
                                        </span>
                                      </span> 
                                    </div>
                                  </div>
                                  <div class="button-pos" >
                                    <div class="text-padding">
                                      <div class="aline-center">
                                        <div class="p-name text-color">
                                          6363
                                        </div>
                                      </div>
                                      <span class="flexy">
                                        <span class="">
                                          OTP
                                        </span>
                                      </span> 
                                    </div>
                                  </div>    
                                </div>
                                        
                                <div><hr class="sf-divider" /></div>

                                <div class="provider-head aline-center side-padding">
                                  <div class="flexy">
                                    <img
                                      class="provide-img"
                                      src="/icons/contactSupport.png"
                                      alt=""
                                      :width="37"
                                      :height="39"
                                    />
                                    <div class="text-padding">
                                      <div class="aline-center">
                                        <div class="p-name">
                                          Manjunath Reddy
                                        </div>
                                      </div>
                                      <span class="flexy">
                                        <div class="rating-css">
                                            4
                                        </div>
                                        <span class="sf-rating__icon">
                                            <SfIcon color="#FADB14" size="16px" icon="star" />
                                        </span>
                                      </span> 
                                    </div>
                                            
                                  </div>
                                  <div class="button-pos" >
                                    <SfButton class="contact">
                                        <img
                                            class="contact-img"
                                            id="icon"
                                            src="/icons/contact.png"
                                            alt="Vue Storefront Next"
                                        />
                                    </SfButton>
                                  </div>    
                                </div>

                                <div class="provider-head aline-center side-padding">
                                  <div class="t-c-l-data">
                                    Temperature  
                                  </div>
                                  <div class="button-pos t-c-l-data" >
                                    97.3<sup><span>&#176;</span></sup>F
                                  </div>    
                                </div>

                                <div class="provider-head aline-center side-padding">
                                  <div class="t-c-l-data">
                                    Last Checked at
                                  </div>
                                  <div class="button-pos t-c-l-data" >
                                    12:34pm
                                  </div>    
                                </div>

                                <div class="provider-head aline-center side-padding">
                                  <div class="t-c-l-data">
                                    Language Known
                                  </div>
                                  <div class="button-pos t-c-l-data" >
                                    Hindi & Kannada
                                  </div>    
                                </div>

                                <div class="provider-head aline-center side-padding">
                    <div class="flexy">
                      <div class="text-padding">
                        <span class="flexy">
                          <span class="rating-css">
                            Total fare
                          </span>
                        </span> 
                        <div class="aline-center">
                          <div class="p-name">
                            ₹80
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="button-pos" >
                      <div class="text-padding">
                        <div class="aline-center">
                          <div>
                            Payment <span class="p-name">Google Pay</span>
                          </div>
                        </div>
                        <span class="flexy text-color">
                          change
                        </span> 
                      </div>
                    </div>    
                                </div>
                                    
                                <div class="provider-head aline-center side-padding">
                                  <div class="flexy">
                                    <SfButton class="provide-img">
                                      <SfIcon class="locationicon" color="#f37a20" size="20px" icon="marker" /> 
                                    </SfButton>
                                    <div class="text-padding1">
                                      <div class="aline-center">
                                        <div class="p-name">
                                          Source
                                        </div>
                                      </div>
                                      <div class="rating-css">
                                        <div>  
                                        <input type="text" :value="_SourceLocation"/>
                                        </div>
                                      </div> 
                                    </div>
                                                        
                                  </div>
                                  <div class="button-pos text-color" >
                                    Edit
                                  </div>    
                                </div>

                                <div class="provider-head aline-center side-padding">
                                  <div class="flexy">
                                    <SfButton class="provide-img">
                                      <SfIcon class="locationicon" color="#2081F3" size="20px" icon="marker" /> 
                                    </SfButton>
                                    <div class="text-padding1">
                                      <div class="aline-center">
                                        <div class="p-name">
                                          Destination
                                        </div>
                                      </div>
                                      <span class="flexy">
                                        <div class="rating-css">
                                          <input type="text" :value="_destloc"/>
                                        </div>
                                      </span> 
                                    </div>
                                                        
                                  </div>
                                  <div class="button-pos text-color" >
                                    Edit
                                  </div>    
                                </div>
                                <br/>
                                <div><hr class="sf-divider" /></div>
                  
                                <SfButton id="btn" @click="isContactSupport = true">
                                  Contact Support
                                  <SfIcon class="button-pos">
                                    <SfImage
                                      id="icon"
                                      src="/icons/contactSupport.png"
                                      alt="Vue Storefront Next"
                                      :width="20"
                                      :height="22"
                                    />
                                  </SfIcon>
                                </SfButton>

                                <nuxt-link :to="localePath('/CancelOrder')">
                                  <div class="cancel-order">
                                    Cancel Order
                                  </div>
                                </nuxt-link>

                                  <!--<BottomSlider :visible="isContactSupport" >
                                    <div>
                                      <div class="modal-heading">Contact Support</div>
                                      <div><hr class="sf-divider" /></div>
                                    </div>
                                    <div class="modal-body">
                                      <div  class="option-container">
                                        <div class="option-head">
                                          you can reach out to one of our customer 
                                          support executives for any help, queries 
                                          or feedback to ABC Mart
                                        </div>

                                        <button
                                          class="sf-button color-primary "                          
                                        >
                                          <div class="f-btn-text">Call us</div>
                                        </button>

                                        <button
                                          class="sf-button color-primary "                          
                                        >
                                          <div class="f-btn-text">Email us</div>
                                        </button>

                                        <button
                                          class="sf-button color-primary "                          
                                        >
                                          <div class="f-btn-text">Chat with us</div>
                                        </button>

                                        <!-<SfRadio
                                          v-for="value in cancelReasonValues"
                                          :key="value"
                                          class="sf-radio--transparent"
                                          :value="value"
                                          :label="value"
                                          :disabled="false"
                                          :selected="selectedReason"
                                          @change="selectedReason = value"
                                        />->
                                      </div>

                                      <!-<button
                                        v-if="!canceltext"
                                        class="sf-button color-primary "
                                        :class="{ 'is-disabled--button': !selectedReason }"
                                        @click="cancelBox"
                                        :disabled="!selectedReason"
                                      >
                                        <div class="f-btn-text">Confirm Cancellation Request</div>
                                      </button>->
                                      <!-<div>
                                        <h4 v-if="canceltext">Booking Cancelled</h4>
                                        <p v-if="canceltext">
                                          Refund will be credited to your account as per refund policy
                                        </p>

                                        <button
                                          v-if="canceltext"
                                          class="sf-button color-primary "
                                          :class="{ 'is-disabled--button': !selectedReason }"
                                          @click="onConfirm"
                                          :disabled="!selectedReason"
                                        >
                                          <div class="f-btn-text">Okay</div>
                                        </button>
                                      </div>->
                                    </div>
                                  </BottomSlider>-->
                                  
                              </div>
                            </template>
                          </div>
                        </div>
                  </template>
                  
                  
                </BottomSlider>

              <!--</div>-->
            </template>
      </div>
    </div>
    </div>
  </div>
</template>
<script>
import {
  SfCircleIcon,
  SfSidebar,
  SfHeading,
  SfButton,
  SfIcon,
  SfProperty,
  SfPrice,
  SfCollectedProduct,
  SfImage,
  SfInput
} from '@storefront-ui/vue';
import ProductCard from '~/components/ProductCard';
import { ref, onBeforeMount, watch } from '@vue/composition-api';
import { useUiState } from '~/composables';
import LoadingCircle from '~/components/LoadingCircle';
import LocationSearchBar from '../components/LocationSearchBar.vue';
import Dropdown from '../components/Dropdown.vue';
import DropdownContent from '../components/DropdownContent.vue';
import BottomSlider from '../components/BottomSlider.vue';

export default {
  name: 'DriverInfo',
  components: {
    BottomSlider,
    SfCircleIcon,
    SfSidebar,
    SfButton,
    SfHeading,
    SfIcon,
    SfProperty,
    SfPrice,
    SfCollectedProduct,
    SfImage,
    ProductCard,
    SfInput,
    LoadingCircle,
    LocationSearchBar,
    Dropdown,
    DropdownContent
  },
  data() {
    return {
      isActive: false
    };
  },
  computed: {
    isLocationSelected() {
      return this.location !== '';
    },
    locationText() {
      return this.location !== '' ? 'Your location' : 'Set location';
    },
    isAuthenticatedUser() {
      return this.currentUser !== null;
    }
  },
  setup(_, { root }) {
    const { toggleSearchVisible } = useUiState();
    //const isShow = ref(false);
    toggleSearchVisible(false);
   
    const goBack = () => {
      root.$router.back();
      toggleSearchVisible(true);
    };
    const trigger = ref(true);
    const currentLocation = () => {
      isLocationdropOpen.value = !isLocationdropOpen.value;
      trigger.value = false;
    };
    const { selectedLocation, updateLocation } = useUiState();
    const isLocationdropOpen = ref(false);
    const isShow = ref(false);
    const isContactSupport = ref(false);
    const location = ref(selectedLocation?.value?.address);
    const currentUser = root.$store.$fire.auth.currentUser;
    const toggleLocationDrop = () => {
      trigger.value = true;
      isLocationdropOpen.value = !isLocationdropOpen.value;
    };
    const toggleIsShow = () => {
      isShow.value = !isShow.value;
    };

    const contactSupport = () => {
      isContactSupport.value = !isContactSupport.value;
    };

    const openHamburger = false;
    const locationSelected = (latitude, longitude, address) => {
      location.value = address;
       selectedLocation.latitude===true
    // this.$eventBus.$emit(`${address}`)
    // localStorage.setItem('pickup', JSON.stringify(address));
      updateLocation({
        latitude: latitude,
        longitude: longitude,
        address: address
      });
    };
    const _SourceLocation=ref(JSON.parse(localStorage.getItem("slocation")));
    const _destloc=ref(JSON.parse(localStorage.getItem("destinationLocation")));
    
    const closeModal = () => {
      isShow.value = false;
    };

    return {
      closeModal,
      contactSupport,
      _SourceLocation,
      _destloc,
      goBack,
      isLocationdropOpen,
      toggleLocationDrop,
      isShow,
      toggleIsShow,
      location,
      locationSelected,
      currentUser,
      openHamburger,
      trigger,
      currentLocation
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
  .icon_back {
    position: absolute;
    left: 0;
    margin: 10px;
  }
}

.loader-circle {
  width: 100%;
  position: fixed;
  z-index: 1;
  top: 110px;
  left: 0;
  height: calc(100vh - 170px);
}

.modal-heading {
  margin: 20px;
  font-size: 20px;
  font-weight: 500;
}

.inputs-container {
  margin-bottom: 28px;
}

.sf-sidebar__content {
  padding: 0 !important;
}

.collected-product-list {
  flex: 1;
}

.a{
    background-color: wheat;
    height: 100%;
    width: 100%;
}
.sf-circle-icon {
  --icon-color: #f37a20;
}
.layout-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.button-pos1 , .bar-pos{
  display: flex;
  align-items: center;
  align-content: center;
  height: 5px;
  padding-left: 158px;
  justify-content: center;
}

.bar-pos{
  padding-left: 0px;
}

.location-icon {
  display: flex;
  width: 125px;
  font-size: 11px;
  font-style: normal;
  font-weight: 400;
  line-height: 13px;
  letter-spacing: 0em;
  text-align: left;
}
.sign-in-text {
  color: #f37a20;
}
.userIcon {
  background-color: #f37a20;
}
.user-cart-content {
  display: flex;
  justify-content: space-between;
  width: 7rem;
}
.profile-tooltip {
  position: relative;
}
.profile-tooltip::before,
.profile-tooltip::after {
  --scale: 0;
  --arrow-size: 10px;
  --tooltip-color: #333;
  position: absolute;
  top: -0.25rem;
  left: 50%;
  transform: translateX(-50%) translateY(var(--translate-y, 0))
    scale(var(--scale));
  transition: 150ms transform;
  transform-origin: bottom center;
}
.profile-tooltip::before {
  --translate-y: calc(-100% - var(--arrow-size));
  content: attr(data-tooltip);
  color: white;
  padding: 0.5rem;
  border-radius: 0.3rem;
  text-align: center;
  width: max-content;
  margin-left: -2rem;
  background: var(--tooltip-color);
}
.profile-tooltip:hover::before,
.profile-tooltip:hover::after {
  --scale: 1;
}
.profile-tooltip::after {
  --translate-y: calc(-1 * var(--arrow-size));
  content: '';
  border: var(--arrow-size) solid transparent;
  border-top-color: var(--tooltip-color);
  transform-origin: top center;
}
.mapdata{
  height: 350px;
  width: 100%; 
}
/*.popover-blk{
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  background: rgb(187, 184, 184);
}*/
.contact{
  border-radius: 50%;
  background-color: rgba(243, 122, 32, 0.14);
  height: 50px;
  width: 50px;
}
img{
  border-radius: 9px;
}
.button-pos {
  position: absolute;
  right: 10px;
  //top: 0;
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
#icon {
  height: 15.62504768371582px;
  width: 15.624926567077637px;
  padding: 4px;
}
/*.sf-button{
  justify-content: center;
  justify-items: center;
  align-content: center;
  text-align: center;
}*/
.driver-data{
  margin-top: 13px;
  //border: 2px solid #838281;
  z-index: 99999;
  background: #FFFFFF;
  box-shadow: 0px -5px 40px rgba(0, 0, 0, 0.1);
  //padding: 15px;
  border-radius: 5px;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
}
input {
  width: 100%;   
  border-top-style: hidden;
  border-right-style: hidden;
  border-left-style: hidden;
  border-bottom-style: hidden;
  //border-bottom-style: groove;
  //border-bottom: #000000;
  //background-color: white;
}
.cancel-order{
  color: #FF5552;
  display: flex;
  //line-height: 19px;
  align-items: center;
  align-content: center;
  justify-content: center;
  padding: 19px;
  font-size: 16px;
  font-family: 'SF Pro Text';
  font-style: normal;
  font-weight: 500;
}
.contact{
    border-radius: 50%;
    background-color: rgba(243, 122, 32, 0.14);
    height: 50px;
    width: 50px;
}
.location-block{
    margin-left: 25px;
}
.display-map{
    height: 250px;
    width: 100%;
    background-color: antiquewhite;
}
.locationicon{
    left: 10%;
    width: 30px;
    height: 30px;
    margin-right: 20px;
}
#btn {
    //top:112px;
    //width: 63px;
    box-shadow: 0px -5px 40px rgba(0, 0, 0, 0.15);
    width: 100%;
    margin-bottom: 0%;
}
.search-bar{
  padding-top: 0px;
  padding-bottom: 0px;
  padding-left: 0px;
  padding-right: 0px;
}
.p-name{
  font-size: 14px;
  font-weight: 700px;
}
.t-c-l-data{
  font-size: 15px;
  font-weight: 400px;
  color:  #000000;
  font-family: 'SF Pro Text';
}
.text-padding1{
  padding-left: 40px;
  width: 80%;
  position: fixed;
}
.text-color{
  color: #f37a20;
}

.modal-body {
    padding: 20px;
    color: #37474f;
    .option-container {
      padding: 0 10px 60px;
      .option-head {
        font-weight: 400;
        font-size: 15px;
        padding-bottom: 20px;
      }
      .sf-radio {
        font-size: 15px;
      }
      .sf-button {
        width: -webkit-fill-available;
        border-radius: 3px;
        margin-bottom: 20px;
      }
    }
  }

</style>
