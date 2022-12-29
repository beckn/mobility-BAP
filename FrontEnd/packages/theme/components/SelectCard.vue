<template>
  <div class="dv">
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
                    <SfButton class="sf-button--pure rect-bar-style">
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
                    <div class="">
                      <template>
                        <div>
                          <div class="provider-head aline-center ">
                            <div class="flexy">
                              <img
                                src="/icons/car.png"
                                alt=""
                                :width="37"
                                :height="39"
                              />

                              <div class="text-padding">
                                <div class="aline-center">
                                  <div class="S-name">
                                    {{ _pName }}
                                  </div>
                                </div>
                                <span class="flexy">
                                  <p clamp="subtext">
                                    5 min away
                                  </p>
                                </span>
                              </div>
                            </div>
                            <div class="s-p-price">
                              ₹ {{ Math.round(_pPrice) }}
                            </div>
                          </div>

                          <div><hr class="sf-divider" /></div>
                          <div>
                            <select class="form-select">
                              <option selected>Ride Now</option>
                              <!--<option value="Schedule">Schedule</option>
                                 <option value="Recurring">Recurring</option>-->
                            </select>
                          </div>
                          <div class="loc1">
                            <div class=" ">
                              <div class="flexy">
                                <SfIcon
                                  class="locationicon"
                                  color="#f37a20"
                                  size="20px"
                                  icon="marker"
                                />

                                <div class="text-padding1">
                                  <div class="aline-center">
                                    <div class="s-name">
                                      Source
                                    </div>
                                  </div>
                                  <div class="rating-css">
                                    <div class="text1">
                                      <input
                                        type="text"
                                        :value="_SourceLocation"
                                        disabled
                                      />
                                    </div>
                                  </div>
                                </div>
                                <br />
                              </div>
                            </div>
                            <div class="">
                              <div class="flexy">
                                <SfIcon
                                  class="locationicon"
                                  color="#2081F3"
                                  size="20px"
                                  icon="marker"
                                />

                                <div class="text-padding1">
                                  <div class="aline-center">
                                    <div class="s-name">
                                      Destination
                                    </div>
                                  </div>

                                  <div class="text1">
                                    <input
                                      type="text"
                                      :value="_destloc"
                                      disabled
                                    />
                                  </div>
                                </div>
                                <br />
                              </div>
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
        <div>
          <template>
            <div class="location-content">
              <BottomSlider :visible="isShow" @close="closeModal">
                <template>
                  <div class="bar" @click="toggleIsShow">
                    <SfButton class="sf-button--pure ">
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
                          <div>
                            <div class="">
                              <template>
                                <div>
                                  <div
                                    class="provider-head aline-center side-padding "
                                  >
                                    <div class="flexy">
                                      <img
                                        src="/icons/car.png"
                                        alt=""
                                        :width="37"
                                        :height="39"
                                      />

                                      <div class="text-padding">
                                        <div class="aline-center">
                                          <div class="S-name">
                                            {{ _pName }}
                                          </div>
                                        </div>
                                        <span class="flexy">
                                          <p clamp="subtext">
                                            5 min away
                                          </p>
                                        </span>
                                      </div>
                                    </div>
                                    <div class="s-p-price">
                                      ₹ {{ Math.round(_pPrice) }}
                                    </div>
                                  </div>

                                  <div><hr class="sf-divider" /></div>
                                  <div>
                                    <select class="form-select">
                                      <option selected>Ride Now</option>
                                      <!--<option value="Schedule">Schedule</option>
                                         <option value="Recurring">Recurring</option>-->
                                    </select>
                                  </div>
                                  <div class="loc">
                                    <div
                                      class=" provider-head aline-cente side-padding"
                                    >
                                      <div class="flexy">
                                        <SfIcon
                                          class="locationicon"
                                          color="#f37a20"
                                          size="20px"
                                          icon="marker"
                                        />

                                        <div class="text-padding1">
                                          <div class="aline-center">
                                            <div class="s-name">
                                              Source
                                            </div>
                                          </div>
                                          <div class="rating-css">
                                            <div class="text1">
                                              <input
                                                type="text"
                                                :value="_SourceLocation"
                                                disabled
                                              />
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                    <div
                                      class="provider-head aline-center side-padding"
                                    >
                                      <div class="flexy">
                                        <SfIcon
                                          class="locationicon"
                                          color="#2081F3"
                                          size="20px"
                                          icon="marker"
                                        />

                                        <div class="">
                                          <div class="">
                                            <div class="s-name">
                                              Destination
                                            </div>
                                          </div>

                                          <div class="text1">
                                            <input
                                              type="text"
                                              :value="_destloc"
                                              disabled
                                            />
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div>
                                    <p class="bookfor">Book for :</p>
                                    <select class="form-select">
                                      <option selected>MySelf</option>
                                    </select>
                                    <div class="form">
                                      <form></form>
                                      <p class="s-name">Name:</p>
                                      <input
                                        @click="enterName"
                                        v-model="name"
                                        class="text1"
                                        type="text"
                                        placeholder="Enter Name"
                                        @keyup="validateName"
                                      />
                                      <div
                                        class="invalid-warning"
                                        v-if="!isValidName"
                                      >
                                        Invalid Name!
                                      </div>
                                      <br />
                                      <br />
                                      <p class="s-name">Phone Number:</p>
                                      <input
                                        @click="enterphoneNo"
                                        v-model="phoneNo"
                                        class="text1"
                                        type="text"
                                        placeholder="Enter phone number"
                                        @keyup="validatePhoneNumber"
                                      />
                                      <div
                                        class="invalid-warning"
                                        v-if="!isValidPhoneNumber"
                                      >
                                        Invalid phone number!
                                      </div>
                                    </div>
                                    <br />

                                    <div><hr class="sf-divider" /></div>
                                    <div
                                      v-if="enableLoader"
                                      key="loadingCircle"
                                      class="loader-circle"
                                    >
                                      <LoadingCircle :enable="enableLoader" />
                                    </div>
                                    <!-- <nuxt-link :to="localePath('/payment')"> -->
                                    <SfButton
                                      :class="{
                                        [_value]: Boolean(_value)
                                          ? ''
                                          : 'is-disabled--button'
                                      }"
                                      :disabled="
                                        !name ||
                                          !phoneNo ||
                                          !isValidPhoneNumber ||
                                          !isValidName
                                      "
                                      type="submit"
                                      id="btn"
                                      @click="onConfirmProc"
                                    >
                                      Confirm & Proceed</SfButton
                                    >
                                  </div>
                                  <!-- </nuxt-link> -->
                                </div>
                              </template>
                            </div>
                          </div>
                        </div>
                      </template>
                    </div>
                  </div>
                </template>
              </BottomSlider>
            </div>
          </template>
          <div></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { SfImage, SfIcon, SfButton, SfSearchBar } from '@storefront-ui/vue';
//import AddToCart from './AddToCart.vue';
import {
  productGetters,
  providerGetters,
  useInitOrder
} from '@vue-storefront/beckn';
import { ref, computed, watch, onMounted } from '@vue/composition-api';
import LoadingCircle from './LoadingCircle';
import { createInitOrderRequest } from '../helpers/helpers';
import helpers from '../helpers/helpers';
import BottomSlider from '../components/ConfirmBottomSlider.vue';
import { root } from 'postcss';
/* eslint camelcase: 0 */
export default {
  name: 'SelectCard',
  components: {
    SfSearchBar,
    SfButton,
    SfImage,
    // AddToCart,
    SfIcon,
    LoadingCircle,
    BottomSlider
  },
  props: {
    product: { type: Object },
    pName: { type: String, default: '' },
    pWieght: { type: String, default: '' },
    pPrice: { type: Number, default: '' },
    pImage: { type: String, default: '' },
    pCount: { type: Number, default: 0 }
  },

  setup(props, { root, emit }) {
    const name = ref('');
    const phoneNo = ref('');
    const isValidPhoneNumber = ref(true);
    const isValidName = ref(true);
    const _pName = computed(() => props.pName);
    const _pWieght = computed(() => props.pWieght);
    const _pPrice = computed(() => props.pPrice);
    const _pImage = computed(() => props.pImage);
    // const _pImage = '/icons/car.svg';
    const enableLoader = ref(false);
    const _pCount = computed(() => props.pCount);
    const _SourceLocation = ref(JSON.parse(localStorage.getItem('slocation')));
    const _destloc = ref(
      JSON.parse(localStorage.getItem('destinationLocation'))
    );

    const enterName = async () => {
      if (localStorage.getItem('experienceId') !== null) {
        try {
          await fetch('https://api.eventcollector.becknprotocol.io/v2/event', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            redirect: 'follow', // manual, *follow, error
            referrerPolicy: 'no-referrer', // no-referrer,
            body: JSON.stringify({
              experienceId: localStorage.getItem('experienceId'),
              eventCode: 'motb_init_details1',
              eventAction: 'initializing the ride by giving my name',
              eventSourceId: '2',
              eventDestinationId: '2',
              payload: '', //add full context object
              eventStart_ts: Date.now()
            })
          });
        } catch (error) {
          console.error(error);
        }
      }
    };

    const enterphoneNo = async () => {
      if (localStorage.getItem('experienceId') !== null) {
        try {
          await fetch('https://api.eventcollector.becknprotocol.io/v2/event', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            redirect: 'follow', // manual, *follow, error
            referrerPolicy: 'no-referrer', // no-referrer,
            body: JSON.stringify({
              experienceId: localStorage.getItem('experienceId'),
              eventCode: 'motb_init_details2',
              eventAction: 'initializing the ride by giving my phone number',
              eventSourceId: '2',
              eventDestinationId: '2',
              payload: '', //add full context object
              eventStart_ts: Date.now()
            })
          });
        } catch (error) {
          console.error(error);
        }
      }
    };

    const validatePhoneNumber = () => {
      const validationRegex = /^\d{10}$/;
      if (phoneNo.value.match(validationRegex)) {
        isValidPhoneNumber.value = true;
      } else {
        isValidPhoneNumber.value = false;
      }
    };
    const validateName = () => {
      const validationRegexName = /^[a-zA-Z ]{2,30}$/;
      if (name.value.match(validationRegexName)) {
        isValidName.value = true;
      } else {
        isValidName.value = false;
      }
    };

    const {
      pollResults: onInitResult,
      poll: onInitOrder,
      init,
      stopPolling
    } = useInitOrder();
    const quoteItems = JSON.parse(localStorage.getItem('quoteData'));
    const transactionId = localStorage.getItem('transactionId');
    const cartItem = JSON.parse(localStorage.getItem('cartItem'));
    const isShow = ref(false);
    const toggleIsShow = () => {
      isShow.value = !isShow.value;
    };
    const closeModal = () => {
      isShow.value = false;
    };

    const onConfirmProc = async () => {
      localStorage.setItem('Name', JSON.stringify(name.value));
      localStorage.setItem('phoneNo', JSON.stringify(phoneNo.value));
      console.log(name.value, phoneNo.value);
      enableLoader.value = true;
      if (quoteItems && transactionId && cartItem) {
        const params = createInitOrderRequest(
          transactionId,
          quoteItems.quote,
          cartItem,
          '12.9063433,77.5856825'
        );
        const response = await init(params, localStorage.getItem('token'));
        if (localStorage.getItem('experienceId') !== null) {
          try {
            await fetch('https://api.eventcollector.becknprotocol.io/v2/event', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              redirect: 'follow',
              referrerPolicy: 'no-referrer',
              body: JSON.stringify({
                experienceId: localStorage.getItem('experienceId'),
                eventCode: 'motb_init_ride',
                eventAction: 'I have initiated the ride request',
                eventSourceId: '2',
                eventDestinationId: '3',
                payload: '', //add full context object
                eventStart_ts: Date.now()
              })
            });
          } catch (error) {
            console.error(error);
          }
        }

        await onInitOrder(
          {
            // eslint-disable-next-line camelcase
            messageIds: response[0].context.message_id
          },
          localStorage.getItem('token')
        );
        //console.log(onInitResult);
      }

      watch(
        () => onInitResult.value,
        async (onInitRes) => {
          if (onInitRes?.error) {
            throw 'api fail';
          }
          if (!onInitRes) {
            return;
          }
          if (helpers.shouldStopPooling(onInitRes, 'order')) {
            stopPolling();
            localStorage.setItem('initResult', JSON.stringify(onInitRes));

            enableLoader.value = false;
            if (localStorage.getItem('experienceId') !== null) {
              try {
                await fetch(
                  'https://api.eventcollector.becknprotocol.io/v2/event',
                  {
                    method: 'POST',
                    headers: {
                      'Content-Type': 'application/json'
                    },
                    redirect: 'follow', // manual, *follow, error
                    referrerPolicy: 'no-referrer', // no-referrer,
                    body: JSON.stringify({
                      experienceId: localStorage.getItem('experienceId'),
                      eventCode: 'motb_sent_fnl_quote',
                      eventAction: 'sent final quote',
                      eventSourceId: '3',
                      eventDestinationId: '2',
                      payload: '', //add full context object
                      eventStart_ts: Date.now()
                    })
                  }
                );
              } catch (error) {
                console.error(error);
              }
            }
            root.$router.push('/payment');
          }
        }
      );
    };
    return {
      closeModal,
      isShow,
      toggleIsShow,
      enableLoader,
      onConfirmProc,
      _SourceLocation,
      _destloc,
      productGetters,
      providerGetters,
      _pName,
      _pWieght,
      _pPrice,
      _pImage,
      _pCount,
      name,
      phoneNo,
      isValidPhoneNumber,
      validatePhoneNumber,
      isValidName,
      validateName,
      enterName,
      enterphoneNo
    };
  }
};
</script>
<style lang="scss" scoped>
.loader-circle {
  margin-bottom: 20px;
}
.invalid-warning {
  // margin: 10px auto;
  color: red;
}
.search-bar {
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
.form {
  width: 55%;
  padding-left: 11%;
}
.bookfor {
  padding-left: 15%;

  font-family: 'Roboto';
  font-style: normal;
  font-weight: 600;
  font-size: 11px;
  line-height: 13px;
  /* identical to box height */

  /* Black/Normal */

  color: #37474f;
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
.button-pos1,
.bar-pos {
  display: flex;
  align-items: center;
  align-content: center;
  height: 5px;
  padding-left: 18%;
  justify-content: center;
}
.bar {
  padding-left: 40%;
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
.loc {
  padding-left: 21px;
}
.loc1 {
  padding-left: 21px;
  padding-bottom: 25px;
}
input {
  width: 140%;
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  color: #37474f;
  line-height: 14px;
}
@media screen and (max-width: 375px) {
  input {
    width: 100%;
    font-family: 'Roboto';
    font-style: normal;
    font-weight: 500;
    font-size: 14px;
    color: #37474f;
    line-height: 14px;
  }
}
.subtext {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  color: #8a8d8e;
}
// .text1{
//   width: 100%;
//   border-bottom: 2px solid
// rgba(67, 70, 78, 1);
// }
#btn {
  //top:112px;
  width: 63px;
  box-shadow: 0px -5px 40px rgba(0, 0, 0, 0.15);
  width: 100%;
  margin-bottom: 0%;
  font-weight: 600;
  text-transform: capitalize;
}
.locationicon {
  // left: 10%;
  width: 30px;
  height: 30px;
  margin-right: 10px;
}
.form-select {
  width: 80%;
  height: 36px;
  padding: 5px 5px;
  color: #f37a20;
  margin: 4%4%4%10%;
  background: #ffffff;
  border: 1px solid #e6e6e6;
  border-radius: 4px;
}
.s-p-price {
  padding: 5px;
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 700;
  font-size: 16px;
  line-height: 19px;
  // position: absolute;
  right: 10%;
  width: 100px;
  text-align: right;
  color: #f37a20;
}
.loc-input {
  font-size: 12px;
  height: 0px;
}
.location-block {
  margin-left: 25px;
}
.display-map {
  height: 250px;
  width: 100%;
  background-color: antiquewhite;
}
img {
  border-radius: 9px;
}
.s-name {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  font-size: 11px;
  color: #37474f;
}
/*.sf-search-bar{
    left: ;
  }*/
.driver-data {
  margin-top: 5px;
  //border: 2px solid #838281;
  z-index: 99999;
  background: #ffffff;
  box-shadow: 0px -5px 40px rgba(0, 0, 0, 0.1);
  //padding: 15px;
  border-radius: 5px;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
}
</style>
