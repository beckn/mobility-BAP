<template>
  <div id="payment">
    <div v-if="enableLoader" key="loadingCircle" class="loader-circle">
      <LoadingCircle :enable="enableLoader" :customText="'confirming order'" />
    </div>
    <div class="top-bar header-top">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div class="">Select Payment Method</div>
    </div>

    <div class="details header-push">
      <div class="sub-heading">
        <div class="p-name">Payment</div>
      </div>
      <Card v-if="!!(order && order.cart)">
        <CardContent
          v-for="breakup in order.cart.quote.breakup"
          :key="breakup.title"
          class="flex-space-bw"
        >
          <div class="address-text">{{ breakup.title }}</div>
          <div class="address-text">₹{{ Math.trunc(breakup.price.value) }}</div>
        </CardContent>
        <div><hr class="sf-divider divider" /></div>
        <CardContent class="flex-space-bw">
          <div class="address-text bold">Total</div>
          <div class="address-text bold">
            ₹{{ Math.trunc(order.cart.quote.price.value) }}
          </div>
        </CardContent>
      </Card>
      <!-- <div class="sub-heading">
        <div class="p-name">Other</div>
      </div> -->
      <Card>
        <CardContent>
          <div class="redo">
            <input
              type="radio"
              class="container"
              :name="'Payment'"
              :value="'Cash'"
              :disabled="false"
              :selected="paymentMethod"
              @change="changePaymentMethod"
            />
            <img
              style="padding-top:8px; padding-left: 10px;"
              src="/icons/money 2.png"
              alt=""
              :width="30"
              :height="30"
            />
            <label class="cash">Cash</label>
          </div>
        </CardContent>
      </Card>
    </div>
    <BookRide
      class="footer-fixed"
      :buttonText="'Book Now'"
      :buttonEnable="isPayConfirmActive"
      :totalPrice="
        parseFloat(
          cartItem.price && cartItem.price.value ? cartItem.price.value : '0'
        ).toFixed(2)
      "
    >
    </BookRide>
  </div>
</template>
<script>
import { SfButton, SfRadio, SfIcon, SfImage } from '@storefront-ui/vue';
import { useUiState } from '~/composables';

import { ref, computed, onBeforeMount, watch } from '@vue/composition-api';

import LoadingCircle from '~/components/LoadingCircle';
// import helpers from '../helpers/helpers';
import { useConfirmOrder, cartGetters } from '@vue-storefront/beckn';

import Card from '~/components/Card.vue';

import BookRide from './BookRide.vue';
import CardContent from '~/components/CardContent.vue';
import helpers, { createConfirmOrderRequest } from '../helpers/helpers';
const { toggleCartSidebar } = useUiState();
export default {
  name: 'Payment',
  components: {
    SfImage,
    SfButton,
    SfIcon,
    Card,
    CardContent,
    SfRadio,
    BookRide,
    LoadingCircle
  },
  methods: {
    openCart() {
      toggleCartSidebar();
    }
  },
  setup(_, context) {
    const cartItem = JSON.parse(localStorage.getItem('cartData')).items[0];

    const paymentMethod = ref('');
    const order = ref({});
    const isOrderVerified = ref(false);
    const enableLoader = ref(false);

    const { init, poll, pollResults, stopPolling } = useConfirmOrder(
      'confirm-order'
    );

    const changePaymentMethod = (value) => {
      paymentMethod.value = value;
    };

    const isPayConfirmActive = computed(() => {
      return paymentMethod.value !== '';
    });
    const confirmRide = async () => {
      enableLoader.value = true;
      const transId = localStorage.getItem('transactionId');
      const initRes = JSON.parse(localStorage.getItem('initResult'));
      const quoteItems = JSON.parse(localStorage.getItem('quoteData'));
      const cartItems = JSON.parse(localStorage.getItem('cartItem'));

      // if (transId && initRes && quoteItems && cartItems) {
      const params = createConfirmOrderRequest(
        transId,
        initRes
          ? initRes[0].message.order
          : {
              provider: {
                id:
                  './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.provider'
              },
              items: [
                {
                  id:
                    './mobility/ind.blr/5@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.item',
                  fulfillment_id:
                    cartItems.bpp_providers[0].items[0].fulfillment_id,
                  category_id:
                    './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.category',
                  descriptor: {
                    name: 'SUV- Wifi-AC-Car-Santro',
                    code: 'SUV- Wifi-AC-Car-Santro'
                  }
                }
              ],
              billing: {
                name: './Rajat/Mr./Rajat/ /Kumar/',
                phone: '+919867654322',
                address: {
                  door: 'MBT',
                  name: 'RajatKumar',
                  building: ',A33',
                  locality: '',
                  city: 'Bengaluru',
                  country: 'IND',
                  area_code: '560078'
                },
                email: 'er.rjtkumar@gmail.com'
              },
              fulfillment: {
                id:
                  './mobility/ind.blr/1635@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.fulfillment',
                tracking: false,
                vehicle: {
                  registration: 'KA05Z 3910'
                },
                start: {
                  location: {
                    gps: '12.901073,77.599115'
                  }
                },
                end: {
                  location: {
                    gps: '12.906343,77.585683'
                  }
                },
                customer: {
                  person: {},
                  contact: {
                    phone: '+919867654322',
                    email: 'er.rjtkumar@gmail.com'
                  }
                }
              },
              quote: {
                price: {
                  currency: 'INR',
                  value: '23.568058651428757'
                },
                breakup: [
                  {
                    title: 'Fare',
                    price: {
                      currency: 'INR',
                      value: '19.972931060532847'
                    }
                  },
                  {
                    title: 'Tax',
                    price: {
                      currency: 'INR',
                      value: '3.5951275908959097'
                    }
                  }
                ]
              },
              id:
                './mobility/ind.blr/1635@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.order',
              state: 'Not Confirmed'
            },
        quoteItems
          ? quoteItems.quote
          : {
              provider: {
                id:
                  './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.provider',
                descriptor: {
                  name: 'Venky Tours'
                },
                locations: [
                  {
                    id:
                      './mobility/ind.blr/5@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.provider_location',
                    gps: '12.922074,77.651177'
                  }
                ],
                categories: [
                  {
                    id:
                      './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.category',
                    descriptor: {
                      name: 'SUV'
                    }
                  }
                ],
                items: [
                  {
                    id:
                      './mobility/ind.blr/5@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.item',
                    fulfillment_id:
                      cartItems.bpp_providers[0].items[0].fulfillment_id,
                    descriptor: {
                      name: 'SUV- Wifi-AC-Car-Santro',
                      code: 'SUV- Wifi-AC-Car-Santro'
                    },
                    price: {
                      currency: 'INR',
                      value: '23.568058651428757'
                    },
                    category_id:
                      './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.category'
                  }
                ]
              },
              items: [
                {
                  id:
                    './mobility/ind.blr/5@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.item',
                  descriptor: {
                    name: 'SUV- Wifi-AC-Car-Santro',
                    code: 'SUV- Wifi-AC-Car-Santro'
                  },
                  price: {
                    currency: 'INR',
                    value: '23.568058651428757'
                  },
                  category_id:
                    './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.category'
                }
              ],
              quote: {
                price: {
                  currency: 'INR',
                  value: '23.568058651428757'
                },
                breakup: [
                  {
                    title: 'Fare',
                    price: {
                      currency: 'INR',
                      value: '19.972931060532847'
                    }
                  },
                  {
                    title: 'Tax',
                    price: {
                      currency: 'INR',
                      value: '3.5951275908959097'
                    }
                  }
                ]
              }
            },
        cartItems
          ? cartItems
          : {
              bpp_descriptor: {
                name: 'taxi.becknprotocol.io',
                code: 'taxi.becknprotocol.io'
              },
              bpp_providers: [
                {
                  id:
                    './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.provider',
                  descriptor: {
                    name: 'Venky Tours'
                  },
                  locations: [
                    {
                      id:
                        './mobility/ind.blr/5@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.provider_location',
                      gps: '12.922074,77.651177'
                    }
                  ],
                  categories: [
                    {
                      id:
                        './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.category',
                      descriptor: {
                        name: 'SUV'
                      }
                    }
                  ],
                  items: [
                    {
                      id:
                        './mobility/ind.blr/5@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.item',
                      fulfillment_id:
                        cartItems.bpp_providers[0].items[0].fulfillment_id,
                      descriptor: {
                        name: 'SUV- Wifi-AC-Car-Santro',
                        code: 'SUV- Wifi-AC-Car-Santro'
                      },
                      price: {
                        currency: 'INR',
                        value: '23.568058651428757'
                      },
                      category_id:
                        './mobility/ind.blr/1@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.category'
                    }
                  ]
                }
              ],
              bpp_fulfillments: [
                {
                  id:
                    './mobility/ind.blr/1635@becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in.fulfillment',
                  tracking: false,
                  vehicle: {
                    registration: 'KA05Z 3910'
                  },
                  start: {
                    location: {
                      gps: '12.901073,77.599115'
                    }
                  },
                  end: {
                    location: {
                      gps: '12.906343,77.585683'
                    }
                  }
                }
              ],
              bpp_id:
                'becknify.humbhionline.in.mobility.BPP/beckn_open/app1-succinct-in',
              bpp_uri:
                'https://becknify.humbhionline.in/mobility/beckn_open/app1-succinct-in/bpp'
            }
      );
      const response = await init(params, localStorage.getItem('token'));
      await poll(
        { messageIds: response[0].context.message_id },
        localStorage.getItem('token')
      );
      // }

      watch(
        () => pollResults.value,
        (newValue) => {
          if (helpers.shouldStopPooling(newValue, 'order')) {
            stopPolling();
            localStorage.setItem(
              'confirmData',
              JSON.stringify(newValue[0].message)
            );
            localStorage.removeItem('cartItem');
            localStorage.removeItem('quoteData');
            localStorage.removeItem('initResult');
            localStorage.setItem(
              'transactionId',
              newValue[0].context.transaction_id
            );
          }
        }
      );
      enableLoader.value = false;
    };

    const goBack = () => context.root.$router.back();

    onBeforeMount(async () => {
      await confirmRide();
      order.value = JSON.parse(localStorage.getItem('orderProgress'));
    });
    return {
      confirmRide,
      cartItem,
      paymentMethod,
      changePaymentMethod,
      order,
      cartGetters,
      goBack,
      isPayConfirmActive,
      enableLoader,
      isOrderVerified
    };
  }
};
</script>
<style lang="scss" scoped>
// .header-top{
//     position: fixed;
//     width: 100%;
//     top: 45px;
//     z-index: 9;
// }
// .header-push{
//     top: 107px;
//     position: relative;
//     padding-bottom: 107px;
// }
.container {
  accent-color: #d77753;

  width: 0.8rem;
}
.cash {
  padding-left: 10px;
  padding-top: 10px;
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
}
.redo {
  display: flex;
}
.Rbtn {
  padding-right: 0px;
}

.icon_back {
  position: absolute;
  left: 0;
  margin: 10px;
}
.details {
  margin: 2px 20px;
}

.p-name {
  font-size: 16px;
  font-weight: 600;
  color: #37474f;
}

.sub-heading {
  margin: 16px 0px;
  display: flex;
  justify-content: space-between;
}

.loader-circle {
  width: 100%;
  position: fixed;
  z-index: 1;
  // top: 130px;
  left: 0;
  height: 95vh;
}
SfRadio {
  padding-right: 0px;
}
.flex-space-bw {
  justify-content: space-between;
}
</style>
