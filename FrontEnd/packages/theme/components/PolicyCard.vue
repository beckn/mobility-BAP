<template>
  <div>
    <div class="card">
      <div class="card-content">
        <div>
          <SfImage :src="_pImage" alt="product img" :width="40" :height="40" />
        </div>
        <div>
          <span class="tittle">{{ _pTittle }}</span>

          <div>
            
          </div>
          <p class="subtittle">{{ _pSubtittle }}</p>
        </div>
      </div>
      <div>
        <span class="icon">
          <br />
          <SfIcon  @click="$emit('goToForm')" color="var(--c-primary)" size="10px" icon="chevron_right" />
        </span>
      </div>
    </div>
  </div>
</template>
<script>
import { SfImage, SfIcon } from '@storefront-ui/vue';
import { ref, computed } from '@vue/composition-api';

export default {
  name: 'ProductCard',
  components: {
    SfImage,
    SfIcon
  },
  props: {
    pSubtittle: { type: String, default: '' },
    pTittle: { type: String, default: '' },
    pImage: { type: String, default: '' }
  },

  setup(props, { emit }) {
    const _pSubtittle = computed(() => props.pSubtittle);
    const _pTittle = computed(() => props.pTittle);
    const _pImage = computed(() => props.pImage);

    return {
      _pImage,
      _pTittle,
      _pSubtittle
    };
  },
  methods: {}
};
</script>
<style scoped>
.card {
  display: flex;
  padding: 10px 25px;
  font-size: 16px;
  box-shadow: 0px 4px 45px 0px rgba(26, 42, 97, 0.06);
  border-radius: 5px;
  justify-content: space-between;
}
div.card:hover {
  border: 1px solid rgba(243, 122, 32, 1);
  border-radius: 5px;
}
.card-content {
  display: flex;
}

.tittle {
    padding-left: 5px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 16px;
  align-items: center;
  color: #000000;
}
.subtittle {
    padding-left: 5px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 400;
  font-size: 10px;
  line-height: 15px;

  color: rgba(0, 0, 0, 0.4);
}
</style>
