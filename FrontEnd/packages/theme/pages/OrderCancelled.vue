<template>
  <div class="cancel-page">
    <div class="top-bar"></div>
    <div class="details">
      <!-- <img src="/icons/cancelOrder.svg" alt="" /> -->
      <div class="head">Booking Cancelled</div>
      <!-- <div class="sub"> -->
      <!-- Refund will be credited to your account as per refund policy. -->
      <!-- </div> -->
      <button class="sf-button color-primary" @click="goBack">
        <div>Go back Home</div>
      </button>
      <button class="sf-button color-light continue" @click="goBack">
        <div class="f-btn-text">Continue Ride</div>
      </button>
    </div>
  </div>
</template>
<script>
import { SfIcon, SfRadio } from '@storefront-ui/vue';
import { useUiState } from '~/composables';
const {
  updatesLocation,
  updatedLocation,
  setName,
  setphoneNo,
  settrackLong,
  settrackLat,
  setquoteData,
  setTransactionId,
  setcartItem,
  setconfirmData,
  setconfirmDataContext,
  setcartData,
  setinitResult
} = useUiState();

export default {
  name: 'OrderCancelled',
  components: {
    SfIcon,
    SfRadio
  },
  setup(_, context) {
    const goBack = () => {
      updatesLocation({
        lat: undefined,
        long: undefined,
        addres: undefined
      });
      updatedLocation({
        late: undefined,
        lng: undefined,
        addresss: undefined
      });
      setName(undefined);
      setphoneNo(undefined);
      settrackLong(undefined);
      settrackLat(undefined);
      setquoteData(undefined);
      setTransactionId(undefined);
      setcartItem(undefined);
      setconfirmData(undefined);
      setconfirmDataContext(undefined);
      setcartData(undefined);
      setinitResult(undefined);

      context.root.$router.push('/');
    };

    return {
      goBack
    };
  }
};
</script>

<style lang="scss" scoped>
.cancel-page {
  position: relative;
  // margin-top: 45px;
  height: calc(100vh - 45px);
  .sf-button {
    width: -webkit-fill-available;
    border-radius: 3px;
  }
  .top-bar {
    height: 60px;
    background: white;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.07);
  }

  .details {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: calc(100vh - 225px);
    padding: 60px 20px;
    overflow: scroll;
    color: #6c6c6e;
    .head {
      font-weight: 500;
      font-size: 24px;
      padding: 35px;
    }
    .sub {
      font-size: 17px;
      padding-bottom: 30px;
      text-align: center;
    }

    .continue {
      margin-top: 25px;
      .f-btn-text {
        color: #f37a20;
      }
    }
  }
}
</style>
