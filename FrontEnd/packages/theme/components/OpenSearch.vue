<template>
  <div>
    <div class="open-search header-top-space">
      <h3>
        Open <br />
        Mobility
      </h3>
      <h4>for All</h4>
      <div class="open-search-input">
        <div class="input1">
          <SfImage
            id="icon"
            src="/icons/Vector.png"
            alt="Vue Storefront Next"
          />

          <label>Pickup: </label>

          <!-- v-on:keyup.enter="openSearch" -->
          <input
            @click="pickupLocation"
            v-model="pickup"
            :valid="false"
            errorMessage="errer"
            type="text"
            placeholder="Enter Pickup"
            v-e2e="'home-search-input'"
          />
        </div>
        <!-- <div class="hr">  <hr style="width:100%;" />
        <SfImage src="/icons/Transport.svg" alt="Vue Storefront Next" /></div> -->
        <div class="hr-theme-slash-2">
          <div class="hr-line"></div>
          <div class="hr-icon">
            <!-- <SfImage src="/icons/Transport.svg" alt="Vue Storefront Next" /> -->
          </div>
        </div>

        <div class="input">
          <SfImage
            id="icon"
            src="/icons/Vector.png"
            alt="Vue Storefront Next"
          />
          <label for=""> Dropoff: </label>

          <input
            @click="dropLocation"
            v-model="message"
            v-on:keyup.enter="openSearch"
            :valid="false"
            errorMessage="errer"
            type="text"
            placeholder="Enter Destination"
            v-e2e="'home-search-input'"
          />
        </div>

        <SfButton
          id="btn"
          class="button-pos sf-button--pure color-primary"
          @click="openSearch"
          :disabled="!selectedLocation.latitude || !selectedLocation.longitude"
          v-e2e="'home-search-button'"
          ><label for="btn">Search Rides</label>
          <!-- <span class="sf-search-bar__icon">
            <SfIcon color="var(--c-text)" size="18px" icon="search" />
          </span> -->
        </SfButton>
      </div>
      <div v-if="errorMsg" class="error-msg">Please fill out this field.</div>
      <div v-if="errorMsg2" class="error-msg">
        Pickup and Drop locations are same!.
      </div>
    </div>
    <template>
      <div class="location-blk d-flex w-100">
        <div class="layout-container">
          <div id="location" class="location-content">
            <SfSidebar
              :visible="!!isLocationdropOpen"
              :button="false"
              title="Set Location"
              @close="toggleLocationDrop"
              class="sidebar sf-sidebar--right"
            >
              <transition name="fade">
                <client-only>
                  <LocationSearchBar
                    :buttonlocation="buttonlocation"
                    @locationSelected="locationSelected"
                    @toggleLocationDrop="toggleLocationDrop"
                    @edit="edit"
                    v-e2e="'app-location-sidebar'"
                  />
                </client-only>
              </transition>
            </SfSidebar>
          </div>
        </div>
      </div>
    </template>

    <div class="sf-footer">
      <SfFooter class="footer">
        <!-- <p><span>By</span> <img src="../assets/images/p-b-phonepe.png" alt="" /> </p> -->
        <p>
          <span class="powered-by">Powered by</span>
          <img src="../assets/images/beckn-logo.png" alt="" />
        </p>
      </SfFooter>
    </div>
  </div>
</template>

<script>
import { SfButton, SfSidebar, SfIcon, SfImage } from '@storefront-ui/vue';
import { useUiState } from '~/composables';
import { SfFooter } from '@storefront-ui/vue';
import { ref } from '@vue/composition-api';
import LocationSearchBar from './LocationSearchBar.vue';

const { selectedLocation, updateLocation } = useUiState();

export default {
  components: {
    SfButton,
    SfIcon,
    SfFooter,
    LocationSearchBar,
    SfSidebar,
    SfImage
  },

  setup(_, context) {
    const pickup = ref('');
    const buttonlocation = ref(false);
    // selectedLocation.latitude && selectedLocation.longitude && typeof window !== 'undefined' ? localStorage.getItem('pickup') : ''

    // const pickup=ref();
    //   if(selectedLocation.latitude || selectedLocation.longitude) {
    //   pickup.value=localStorage.getItem('pickup');
    // }

    const location = ref(true);
    const message = ref('');
    const errorMsg = ref(false);
    const errorMsg2 = ref(false);
    const edit = () => {
      if (location.value) {
        pickup.value = '';
        isLocationdropOpen.value = !isLocationdropOpen.value;
      } else if (!location.value) {
        message.value = '';
        isLocationdropOpen.value = !isLocationdropOpen.value;
      }
    };
    const locationSelected = (latitude, longitude, address) => {
      if (location.value) {
        pickup.value = address;

        localStorage.setItem('slocation', JSON.stringify(pickup.value));
        localStorage.setItem('pickUpLatAndLong', `${latitude},${longitude}`);
      } else if (!location.value) {
        message.value = address;

        localStorage.setItem(
          'destinationLocation',
          JSON.stringify(message.value)
        );
        localStorage.setItem('dropLatAndLong', `${latitude},${longitude}`);
      } else if (pickup.value === message.value) {
        message.value = '';
      }

      updateLocation({
        latitude: latitude,
        longitude: longitude,
        address: address
      });
    };

    // console.log(selectedLocation);
    const isLocationdropOpen = ref(false);
    const toggleLocationDrop = () => {
      isLocationdropOpen.value = !isLocationdropOpen.value;
    };
    const pickupLocation = () => {
      buttonlocation.value = true;
      location.value = true;
      isLocationdropOpen.value = !isLocationdropOpen.value;
    };
    const dropLocation = () => {
      buttonlocation.value = false;
      location.value = false;
      isLocationdropOpen.value = !isLocationdropOpen.value;
    };

    const openSearch = () => {
      if (message.value && pickup.value && message.value != pickup.value) {
        if (errorMsg.value) errorMsg.value = false;
        if (errorMsg2.value) errorMsg2.value = false;

        context.root.$router.push({
          name: 'Search',
          params: {
            searchKey: message.value,
            pickuploc: pickup.value
          }
        });
      } else if (!message.value || !pickup.value) {
        errorMsg.value = true;
        errorMsg2.value = false;
      } else if (message.value === pickup.value) {
        errorMsg2.value = true;
      }
    };

    return {
      pickupLocation,
      selectedLocation,
      message,
      errorMsg,
      errorMsg2,
      openSearch,
      pickup,
      isLocationdropOpen,
      locationSelected,
      location,
      dropLocation,
      toggleLocationDrop,
      buttonlocation,
      edit
    };
  }
};
</script>

<style lang="scss" scoped>
// .header-top-space{
//   top: 107px;
// }
.open-search {
  @media (min-width: 560px) {
    padding-top: 40px;
    width: 50%;
    margin: auto;
  }
  #icon {
    padding-right: 5px;
    padding-top: 3px;
  }
  #btn {
    width: 328px;
    height: 48px;
    background: #f37a20;
    border-radius: 4px;
    width: 100%;    
    label{
      font-weight: 600;
      letter-spacing: 0.8px;
    }
  }

  .hr-theme-slash-2 {
    display: flex;
    margin-bottom: 0px;

    .hr-line {
      width: 100%;
      position: relative;

      margin: 11px;
      border-bottom: 1px solid rgba(196, 196, 196, 0.4);
    }
    .hr-icon {
      position: relative;
      top: 11px;
    }
  }
  // label {
  //   font-family: 'Inter', sans-serif;
  //   font-style: normal;
  //   font-weight: 500;
  //   font-size: 16px;
  //   line-height: 19px;
  // }
  .input {
    display: flex;
    padding-top: 5%;
    padding-right: 5%;
    padding-bottom: 15%;
    input::placeholder{
      font-weight: 300;
      font-size: 14px;
    }
  }
  .input1 {
    display: flex;
    padding-top: 15%;
    padding-right: 5%;
    input::placeholder{
      font-weight: 300;
      font-size: 14px;
    }
  }
  .location-drop input {
    font-size: 15px;
    font-weight: 600;
    height: 35px;
  }

  padding: 40px 20px;
  h3 {
    font-style: normal;
    font-weight: 800;
    font-size: 40px;
    line-height: 110%;
    text-align: center;
    /* or 44px */

    letter-spacing: -0.03em;

    color: #f37a20;
  }
  h4 {
    text-align: center;
    font-size: 27px;
    font-weight: 800;
    line-height: 30px;
  }
  p {
    font-size: 15px;
    font-weight: 400;
    line-height: 20px;
    color: #7c7c7c;
    margin-bottom: 30px;
  }
  .open-search-input {    
    // display: flex;
    margin-bottom: 8px;
    // position: relative;
    &.disable {
      h4 {
        padding: 20px;
      }
      button {
        background: #bfbfbf;
        .sf-icon {
          --icon-color: #fff !important;
        }
      }
    }
    input {
      border-radius: 6px;
      width: 100%;
      box-sizing: border-box;
      border: none;
      font-weight: 700;
      font-family: 'Inter', sans-serif;
      font-size: 12px;
      padding: 2px 0 0 4px;
    }
    label {
      font-family: 'Inter', sans-serif;
      font-style: normal;
      font-weight: 500;
      font-size: 14px;
      line-height: 22px;
    }

    button {
      width: 100%;
      position: relative;
      padding: 17px;
      height: 63px;
      top: 0;
      color: #fbfcff;
      // background: #F37A20;
      border-radius: 6px;
      // border-bottom-right-radius: 6px;
      right: 0;
      .sf-icon {
        --icon-color: #fff !important;
      }
    }
  }
  .error-msg {
    font-size: 14px;
    color: #d12727;
  }
}
.sf-footer {
  text-align: center;
  background: #fbfcff !important;
  position: fixed;
  bottom: 0px;
  width: 100%;
  padding: 0;
  p {
    margin: 0;
    span {
      font-size: 17px;
      position: relative;
      top: -6px;
      &.powered-by {
        font-size: 10px;
        top: -1px !important;
      }
    }
  }
}
.layout-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
</style>
