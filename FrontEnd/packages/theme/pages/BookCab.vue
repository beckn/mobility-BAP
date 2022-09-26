<template>
  <client-only>
    <div class="location-content">
      <client-only>
        <div class="s-p-addcart" @click="toggleLocationDrop">
          <button
            class="color-primary sf-button add-btn"
            @click="changeItemNumber('add')"
          >
            Select
          </button>
        </div>
      </client-only>
      <template>
        <div v-if="isQuoteData" id="location" class="location-drop">
          <SfSidebar
            :visible="!!isLocationdropOpen"
            :button="false"
            title="Set Location"
            @click="goBack"
            @close="toggleLocationDrop"
            class="sidebar sf-sidebar--right"
          >
            <transition name="fade">
              <client-only>
                <LocationSearch
                  :b_name="b_name"
                  @locationSelected="locationSelected"
                  @toggleLocationDrop="toggleLocationDrop"
                  v-e2e="'app-location-sidebar'"
                />
              </client-only>
            </transition>
          </SfSidebar>
        </div>
      </template>
    </div>
  </client-only>
</template>
<script>
import { SfCircleIcon, SfButton, SfSidebar, SfIcon } from '@storefront-ui/vue';
import { ref, watch } from '@vue/composition-api';
import LocationSearch from '../components/LocationSearch.vue';
import ModalComponent from '../components/ModalComponent.vue';
import { useUiState } from '~/composables';
import { useQuote } from '@vue-storefront/beckn';
import Dropdown from '../components/Dropdown.vue';
import DropdownContent from '../components/DropdownContent.vue';
import helpers from '../helpers/helpers';
export default {
  name: 'Location',
  components: {
    SfCircleIcon,
    SfButton,
    SfSidebar,
    SfIcon,
    LocationSearch,
    ModalComponent,
    Dropdown,
    DropdownContent
  },
  props: {
    isDisabled: {
      type: Boolean,
      default: false
    },
    value: { type: Number, default: 1 },
    maxLimit: { type: Number, default: 100 }
  },
  data() {
    return {
      isActive: false
    };
  },
  setup(props, { root, emit }) {
    const { selectedLocation, updateLocation } = useUiState();
    const isLocationdropOpen = ref(false);
    const _value = ref(props.value);
    const _maxLimit = ref(props.maxLimit);
    const isShow = ref(false);
    const location = ref(selectedLocation?.value?.address);
    const currentUser = root.$store.$fire.auth.currentUser;
    const b_name = ref('selectcab');
    const toggleLocationDrop = () => {
      isLocationdropOpen.value = !isLocationdropOpen.value;
    };
    const { init, poll, pollResults, stopPolling, polling } = useQuote();
    const isQuoteData = ref(false);

    const goBack = () => {
      root.$router.back();
      toggleSearchVisible(true);
    };
    const toggleIsShow = () => {
      isShow.value = !isShow.value;
    };
    const openHamburger = false;
    const locationSelected = (latitude, longitude, address) => {
      location.value = address;
      // toggleLocationDrop();
      updateLocation({
        latitude: latitude,
        longitude: longitude,
        address: address
      });
    };
    const getQuote = async () => {
      //params for getQuote API
      const cartItems = JSON.parse(localStorage.getItem('cartItem'));
      if (cartItems) {
        const getQuoteRequest = [
          {
            context: {
              // eslint-disable-next-line camelcase
              bpp_id: cartItems.bpp_id,
              // eslint-disable-next-line camelcase
              bpp_uri: cartItems.bpp_uri
            },
            message: {
              cart: {
                items: cartItems.bpp_providers[0].items
              }
            }
          }
        ];

        const responseQuote = await init(
          getQuoteRequest,
          localStorage.getItem('token')
        );
        const msgId = responseQuote[0].context.message_id;
        await poll({ messageIds: msgId }, localStorage.getItem('token'));
      }
      // Loops over the onGetQuote response and checks for error object. If any error then throws 'api fail'
      const handleOnGetQuoteError = (onGetQuoteRes) => {
        onGetQuoteRes.forEach((onGetQuoteRes) => {
          if (onGetQuoteRes.error) {
            throw 'api fail';
          }
        });
      };

      watch(
        () => pollResults.value,
        (onGetQuoteRes) => {
          if (!polling.value || !onGetQuoteRes) {
            return;
          }

          handleOnGetQuoteError(onGetQuoteRes);

          if (helpers.shouldStopPooling(onGetQuoteRes, 'quote')) {
            stopPolling();
            localStorage.setItem(
              'quoteData',
              JSON.stringify(onGetQuoteRes[0].message)
            );
            localStorage.setItem(
              'transactionId',
              onGetQuoteRes[0].context.transaction_id
            );
            isQuoteData.value = true;
          }
        }
      );
    };

    const changeItemNumber = (type) => {
      emit('updateItemCount', _value);
      //console.log("updatecount");
      getQuote();
    };

    return {
      isQuoteData,
      b_name,
      changeItemNumber,
      _value,
      _maxLimit,
      isLocationdropOpen,
      toggleLocationDrop,
      isShow,
      toggleIsShow,
      location,
      locationSelected,
      currentUser,
      openHamburger,
      goBack,
      getQuote
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
  }
  /* mounted:{
    show() {
      return  localStorage.removeItem('cartData');
    }
  }*/
};
</script>
<style lang="scss" scoped>
.sf-circle-icon {
  --icon-color: #f37a20;
}
.layout-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.notShown {
  visibility: hidden !important;
  position: absolute;
}
.button-pos {
  display: flex;
  align-items: center;
  height: 5px;
  padding-left: 5px;
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
</style>
