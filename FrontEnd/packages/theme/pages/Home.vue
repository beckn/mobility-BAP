<template>
  <div id="">
    <OpenSearch />
    <div @click="openCart"></div>
  </div>
</template>
<script>
import OpenSearch from '../components/OpenSearch';
import { useUiState } from '~/composables';
import { onMounted } from '@vue/composition-api';
import helpers from '../helpers/helpers';
import { useCart } from '@vue-storefront/beckn';

const { toggleCartSidebar } = useUiState();
export default {
  name: 'Home',
  components: {
    OpenSearch
  },
  methods: {
    openCart() {
      toggleCartSidebar();
    }
  },
  setup() {
    const { load } = useCart();
    onMounted(() => {
      // if (localStorage.getItem('reloaded')) {
      //   // The page was just reloaded. Clear the value from local storage
      //   // so that it will reload the next time this page is visited.
      //   localStorage.removeItem('reloaded');
      // } else {
      //   // Set a flag so that we know not to reload the page twice.
      //   localStorage.setItem('reloaded', '1');
      //   location.reload();
      // }
      //window.location.reload()
      if (localStorage.getItem('cartData')) {
        const cartData = JSON.parse(localStorage.getItem('cartData'));
        const days = helpers.calculateDays(cartData.cartTime, new Date());
        if (days > 7) {
          localStorage.removeItem('cartData');
          localStorage.removeItem('transactionId');
        }
      }
      load();
    });
  }
};
</script>
<style>
#home {
  padding-top: 15%;
}
</style>
