<template>
  <no-ssr>
    <div class="location-blk d-flex w-100">
      <SfCircleIcon
        class="sf-circle-icon--large left-pos"
        aria-label="marker"
        icon="marker"
        icon-size="18px"
      />
      <div class="layout-container">
        <div class="location-content">
          <client-only>
              <div class="position-relative">
            <div  class="btn"  @click="toggleLocationDrop" >
          <SfButton id="btn">   
            <span class="sf-vector__icon">
                  <SfIcon color="#000" size="20px" icon="marker" />
            </span>
            ENABLE LOCATION
          </SfButton> 
        </div>
        </div>
            <div
                @click="toggleLocationDrop"
                v-e2e="'app-header-location-modal-input-div'"
            >
              <input
                v-model="location"
                type="text"
                aria-label="Select Location"
                class="
                  sf-header__search
                  sf-search-bar
                  sf-header__search
                  be-search-location
                "
                disabled="isActive"
                v-e2e="'app-header-location-input'"
              />
            </div>
          </client-only>
          <template>
            <div id="location" class="location-drop">
              <SfSidebar
                :visible="!!isLocationdropOpen"
                :button="false"
                title="Set Location"
                @close="toggleLocationDrop"
                class="sidebar sf-sidebar--right"
              >
                <transition name="fade">
                  <client-only>
                    <LocationSearch
                      @locationSelected="locationSelected"
                      @toggleLocationDrop="toggleLocationDrop"
                      v-e2e="'app-location-sidebar'"
                    />
                  </client-only>
                </transition>
              </SfSidebar>
            </div>
          </template>
          <div class="popover-blk">
            <template>
              <div  @click="toggleIsShow">
                
              </div>
            </template>
          </div>
        </div>
        
      </div>
    </div>
  </no-ssr>
</template>
<script>
import { SfCircleIcon, SfButton, SfSidebar, SfIcon } from '@storefront-ui/vue';
import { ref } from '@vue/composition-api';
import LocationSearch from '../components/LocationSearch.vue';
import ModalComponent from '../components/ModalComponent.vue';
import { useUiState } from '~/composables';
import Dropdown from '../components/Dropdown.vue';
import DropdownContent from '../components/DropdownContent.vue';
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
    }
  },
  data() {
    return {
      isActive: false
    };
  },
  setup(props, { root }) {
    const { selectedLocation, updateLocation } = useUiState();
    const isLocationdropOpen = ref(false);
    const isShow = ref(false);
    const location = ref(selectedLocation?.value?.address);
    const currentUser = root.$store.$fire.auth.currentUser;
    const toggleLocationDrop = () => {
      isLocationdropOpen.value = !isLocationdropOpen.value;
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
    return {
      isLocationdropOpen,
      toggleLocationDrop,
      isShow,
      toggleIsShow,
      location,
      locationSelected,
      currentUser,
      openHamburger
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
