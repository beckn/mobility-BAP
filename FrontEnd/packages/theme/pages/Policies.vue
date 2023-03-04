<template>
  <div>
    <div class="top-bar header-top">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div>
        Policies
      </div>
    </div>

    <div style="padding: 10px;">
      <label for="Filter"> <span>Filter:</span> </label>
      <select
        class="custom-select"
        name="Filter"
        id="Filter"
        @change="switchSelect($event)"
      >
        <option value="All"> All</option>
        <option value="New">
          New
        </option>
        <option value="Applied">
          Applied
        </option>
        <option value="Disputed">Disputed</option>
      </select>
    </div>
    <hr />

    <div v-if="Applied">
      <div
        style="padding:15px;"
        v-for="(policy, idx) in AppliedArray"
        :key="idx"
      >
        <PolicyCard
          :Applied="true"
          :pImage="policy.Icon"
          :pTittle="policy.tittle"
          :pType="policy.Type"
          :Edate="policy.Edate"
          :Sdate="policy.Sdate"
          @goToForm="goToForm"
        />
      </div>
    </div>
    <div v-if="Disputed">
      <div
        style="padding:15px;"
        v-for="(policy, idx) in DisputedArray"
        :key="idx"
      >
        <PolicyCard
          :Disputed="true"
          :pImage="policy.Icon"
          :pTittle="policy.tittle"
          :pType="policy.Type"
          :Edate="policy.Edate"
          :Sdate="policy.Sdate"
          @goToForm="goToForm"
        />
      </div>
    </div>
    <div v-if="New">
      <div style="padding:15px;" v-for="(policy, idx) in NewArray" :key="idx">
        <PolicyCard
          :New="true"
          :pImage="policy.Icon"
          :pTittle="policy.tittle"
          :pSubtittle="policy.subtittle"
          :Edate="policy.Edate"
          :Sdate="policy.Sdate"
          @goToForm="goToForm"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { reactive, ref } from '@vue/composition-api';
import { SfIcon, SfRadio } from '@storefront-ui/vue';
import PolicyCard from '~/components/PolicyCard.vue';

export default {
  name: 'Policies',

  components: {
    PolicyCard,
    SfIcon,
    SfRadio
  },
  setup(_, context) {
    // TODO REMOVE MOCK DATA AFTER INTIGRATING API
    let AppliedArray = reactive([
      {
        Icon: '/icons/document.png',
        tittle: 'Quarantine Zone',
        Type: ' Geofence',
        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      },
      {
        Icon: '/icons/document.png',
        tittle: 'Applied policy 2',
        Type: ' Geofence',
        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      },
      {
        Icon: '/icons/document.png',
        tittle: 'policy 3',
        Type: ' Geofence',
        Sdate: '11-2-2022',

        Edate: '21-5-2023'
      }
    ]);
    // TODO REMOVE MOCK DATA AFTER INTIGRATING API
    let DisputedArray = reactive([
      {
        Icon: '/icons/document.png',
        tittle: 'Disputed policy',
        Type: ' Geofence',

        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      },
      {
        Icon: '/icons/document.png',
        tittle: 'Disputed policy 2',
        Type: ' Geofence',

        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      },
      {
        Icon: '/icons/document.png',
        tittle: 'Disputed policy 3',
        Type: ' Geofence',

        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      }
    ]);
    // TODO REMOVE MOCK DATA AFTER INTIGRATING API
    let NewArray = reactive([
      {
        Icon: '/icons/document.png',
        tittle: 'New policy',
        Type: ' Geofence',
        Sdate: '11-2-2022',

        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      },
      {
        Icon: '/icons/document.png',
        tittle: 'New policy 2',
        Type: ' Geofence',

        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      },
      {
        Icon: '/icons/document.png',
        tittle: 'New policy 3',
        Type: ' Geofence',

        Sdate: '11-2-2022',
        Edate: '21-5-2023'
      }
    ]);

    const switchSelect = (event) => {
      if (event.target.value === 'All') {
        Allpolicy();
      } else if (event.target.value === 'New') {
        Newpolicy();
      } else if (event.target.value === 'Applied') {
        Appliedpolicy();
      } else if (event.target.value === 'Disputed') {
        Disputedpolicy();
      }
    };
    const goBack = () => {
      context.root.$router.back();
    };
    const goToForm = () => {
      context.root.$router.push('QuarantineZone');
    };
    const Applied = ref(true);
    const New = ref(true);
    const Disputed = ref(true);

    const Appliedpolicy = () => {
      Applied.value = true;
      New.value = false;
      Disputed.value = false;
    };
    const Newpolicy = () => {
      Applied.value = false;
      New.value = true;
      Disputed.value = false;
    };
    const Disputedpolicy = () => {
      Applied.value = false;
      New.value = false;
      Disputed.value = true;
    };
    const Allpolicy = () => {
      Applied.value = true;
      New.value = true;
      Disputed.value = true;
      console.log('hi sujit');
    };

    return {
      AppliedArray,
      DisputedArray,
      NewArray,
      Applied,
      New,
      Disputed,
      Appliedpolicy,
      Newpolicy,
      Disputedpolicy,
      Allpolicy,
      goBack,
      switchSelect,
      goToForm
    };
  }
};
</script>

<style lang="scss" scoped>
.active {
  padding: 12px 12px 0px 12px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 12px;
  line-height: 16px;
  align-items: center;
  color: #000000;
  text-decoration: 3.6px underline rgba(243, 122, 32, 1);
  cursor: pointer;
  margin-top: 10px;
  margin-bottom: 0px;
}
.custom-select {
  width: 133px;
  height: 35px;
  background: #ffffff;
  border: 1px solid #9c9c9c;
  border-radius: 5px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 15px;
  /* identical to box height */
}
//.custom-select  option :hover{ color:orangered; }

option {
  box-shadow: 0 0 10px 100px orangered;
}

hr {
  height: 0px;
  border: 1px solid
    linear-gradient(
      90deg,
      rgba(0, 78, 146, 0.3) 0.42%,
      rgba(0, 4, 40, 0.3) 100%
    );
}
.top-bar {
  padding-right: 42%;
  padding-left: 10px;
  margin-bottom: 25px;
  align-items: center;
  display: flex;
  font-size: 18px;
  justify-content: space-between;
  height: 60px;
  font-weight: 500;
  background: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.07);
}
span {
  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 15px;
  /* identical to box height */

  color: #333333;
}

.inactive {
  padding: 12px 12px 0px 12px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 12px;
  line-height: 16px;
  cursor: pointer;

  align-items: center;
  padding-bottom: 0px;

  color: #000000;
  color: rgba(0, 0, 0, 0.4);
}
</style>
