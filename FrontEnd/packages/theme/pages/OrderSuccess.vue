<template>
  <div id="payment">
    <div class="top-bar header-top"></div>
    <div class="content  header-push">
      <div class="sub-heading">
        <div ><img src="/icons/ratingSubmitted.svg" /></div>
        
      </div>
      <div class="details">
        <div class="sub-heading dis-block">
          <div class="sub-heading">
          Your trip has completed
        </div>
          <div class="p-name flex-set">Thank you! You have arrived your
</div>
          <div class="p-name flex-set">
destination</div>
        </div>
      
        <button
          class="sf-button button color-primary"
          link=""
          @click="goToOrderDetails"
        >
          <!-- button icon template  -->
          <slot name="buttonIcon" />
          <div class="f-btn-text">Continue Ride</div>
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import {
  SfButton,
  SfRadio,
  SfIcon
} from '@storefront-ui/vue';
import { onMounted, onUnmounted } from '@vue/composition-api';

// import { ref, computed } from '@vue/composition-api';
// import { onMounted } from '@vue/composition-api';
// import helpers from '../helpers/helpers';
// import { useCart } from '@vue-storefront/beckn';

import Card from '~/components/Card.vue';

import Footer from '~/components/Footer.vue';
import CardContent from '~/components/CardContent.vue';
export default {
  name: 'OrderSuccess',
  components: {
    SfButton,
    SfIcon,
    Card,
    CardContent,
    SfRadio,
    Footer
  },
  setup(_, context) {
   // const transactionId = context.root.$route.query.id;

    const goToOrderDetails = ()=>{

      context.root.$router.push({
        path: '/',
        // query: {
        //   id: transactionId
        // }
      });
      
//       onUnmounted(() => {
//         console.log("hi guys")
//           setInterval(goToOrderDetails,3000);
// })
// onMounted(() => {
//   console.log("hi guys")
//   context.root.$router.push({
//         path: '/',
//         // query: {
//         //   id: transactionId
//         // }
//       });
      
// }, 2000 )
   //setTimeout(context.root.$router.push('/'),2000)
    };
    return {
      goToOrderDetails
    };
  }
};
</script>
<style lang="scss" scoped>
  img{
    padding-top: 15%;
    padding-right: 5%;
  }
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

.dis-block{
    display: block !important;
}

.details {
  margin: 2px 20px;
}

.content {
  margin: 2px 20px;
}

.p-name {
  
font-style: normal;
font-weight: 400;
font-size: 17px;
line-height: 21px;
/* or 124% */

text-align: center;

color: #6C6C6E;

}

.button {
  width: 100%;
  border-radius: 5px;
}

.flex-set {
  display: flex;
  justify-content: space-around;
}

.sub-heading {
  margin: 34px 0px;
  display: flex;
  justify-content: space-around;
  font-style: normal;
font-weight: 500;
font-size: 24px;
line-height: 25px;
/* identical to box height, or 104% */

text-align: center;

color: #37474F;
}
</style>
