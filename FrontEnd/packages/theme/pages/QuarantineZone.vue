<template>
  <div>
    <div class="top-bar header-top">
      <div @click="goBack" class="sf-chevron--left sf-chevron icon_back">
        <span class="sf-search-bar__icon">
          <SfIcon color="var(--c-primary)" size="20px" icon="chevron_left" />
        </span>
      </div>
      <div>
        Quarantine Zone
      </div>
    </div>

    <div class="quarantine_page">
      <div class="Form">
        <div class="flexform">
          <div>
            <label class="lableclass">Name</label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="Name"
              errorMessage="errer"
              type="text"
              placeholder="Quarantine Zone"
            />
          </div>
          <div>
            <label class="lableclass">Owner </label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="Owner"
              errorMessage="errer"
              type="text"
              placeholder="Ministry of Transport"
            />
          </div>
          <div>
            <label class="lableclass">Type </label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="Type"
              errorMessage="errer"
              type="text"
              placeholder="Geofence "
            />
          </div>
        </div>
        <br />
        <div>
          <label class="lableclass">Description </label>
          <br />
          <textarea
            disabled="true"
            :value="Description"
            errorMessage="errer"
            type="text"
            placeholder="Lorem ipsum dolor sit amet consectetur. Non et quisque praesent justo phasellus. Gravida malesuada donec sit ultrices. Non pharetra dictumst nunc fringilla. Nunc facilisi."
          />
        </div>
        <br />

        <div class="flexform">
          <div>
            <label class="lableclass">Country </label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="Country"
              errorMessage="errer"
              type="text"
              placeholder="India"
            />
          </div>

          <div>
            <label class="lableclass">City :</label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="City"
              errorMessage="errer"
              type="text"
              placeholder="Gurugram"
            />
          </div>
          <div>
            <label class="lableclass">From </label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="FromDate"
              errorMessage="errer"
              type="date"
              placeholder="date"
            />
          </div>
          <div>
            <label class="lableclass">To </label>
            <br />
            <input
              class="flexinputbox"
              disabled="true"
              :value="ToDate"
              errorMessage="errer"
              type="date"
              placeholder="date"
            />
          </div>
        </div>

        <div>
          <label class="lableclass">Policy Document </label>
          <br />
          <a class="link" v-bind:href="url"
            >https:beckn.in/quarantinezone.pdf 1
          </a>
          <br />
          <a class="link" v-bind:href="url"
            >https:beckn.in/T&C/quarantinezone.pdf 1
          </a>
        </div>
        <br />
        <div>
          <label class="lableclass">Applicable to </label>
          <br />
          <input
            class="flexinputbox"
            disabled="true"
            :value="Applicable"
            errorMessage="errer"
            type="text"
            placeholder="BAP; BPP"
          />
        </div>
        <div>
          <label class="lableclass">Geofence: </label>
          <br />
          <nuxt-link :to="localePath('Geofence')"
            ><label class="link">Click to View</label></nuxt-link
          >
        </div>
        <SfButton
          id="btn"
          class="button-pos sf-button--pure color-primary"
          @click="contactSupport"
          v-e2e="'home-search-button'"
          ><label for="btn">Apply</label>
        </SfButton>
        <br />
        <div>
          <button class="color-primary btnclass" @click="goBack()">
            <div class="f-btn-text"><label> Dispute</label></div>
          </button>
        </div>
      </div>
      <div>
        <template>
          <ContactSupportSlider
            :visible="isContactSupport"
            @close="isContactSupport = false"
          >
            <template>
              <div class="bar-pos" @click="contactSupport">
                <SfButton class="sf-button--pure rect-bar-style">
                  <SfImage
                    src="/icons/Rectangle-bar.png"
                    :width="60"
                    :height="5.5"
                    alt="Rectangle bar"
                  />
                </SfButton>
              </div>
              <div>
                <div class="modal-heading">Dispute Policy</div>
                <div><hr class="sf-divider" /></div>
              </div>
              <div class="modal-body">
                <div class="option-container">
                  <p class="warningtext">
                    <br />
                    To raise a dispute against this policy,<br />
                    please send an email to <br />
                    policy@openmobilitynetwork.com
                    <br />
                    <br />
                  </p>
                  <SfButton
                    class="support-btns"
                    @click="goHome"
                    aria-label="Close modal"
                    type="button"
                    >Go back to Home</SfButton
                  >
                </div>
              </div>
            </template>
          </ContactSupportSlider>
        </template>
      </div>

      <!-- <div v-show="!showForm" class="mapSection">
        <div class="toggle-date">
          <div>
            <label class="lableclass">Type :</label>
            <br />
            <div>
              <span
                class=" toggleSwitch1 "
                :class="{ active: ActivePolicy, inactive: !ActivePolicy }"
                @click="activepolicy"
              >
                <label class="lableclass" @click="activepolicy">Active </label>
              </span>
              <span
                class=" toggleSwitch2 "
                :class="{ active: !ActivePolicy, inactive: ActivePolicy }"
                @click="inactivepolicy"
              >
                <label class="lableclass" @click="inactivepolicy"
                  >Inactive
                </label></span
              >
            </div>
          </div>

          <div>
            <label class="lableclass">Policy Date : </label>
            <br />
            <input
              class="lableclass"
              disabled="true"
              :value="PolicyDate"
              errorMessage="errer"
              type="date"
              placeholder="date"
            />
          </div>
        </div>
        <div>
          <label class="lableclass"> Selected Zone :</label>

          <div>
            <policyMap />
          </div>
        </div>
      </div> -->
    </div>
  </div>
</template>
<script>
import { reactive, ref } from '@vue/composition-api';
import { SfIcon, SfRadio, SfButton, SfImage } from '@storefront-ui/vue';
import ContactSupportSlider from '../components/ContactSupportSlider.vue';

export default {
  name: '',

  components: {
    SfImage,
    SfIcon,
    SfRadio,
    SfButton,
    ContactSupportSlider
  },
  setup(_, context) {
    const Description = ref();
    const Type = ref();
    const Domain = ref();
    const Rules = ref();
    const Country = ref();
    const Pincode = ref();
    const City = ref();
    const Applicable = ref();
    const Document = ref();
    const FromDate = ref();
    const ToDate = ref();
    const Owner = ref();
    const Name = ref();
    const goBack = () => {
      context.root.$router.back();
    };
    const showForm = ref(true);
    const showMap = () => {
      showForm.value = false;
    };
    const goHome = () => {
      context.root.$router.push('/');
    };
    const ActivePolicy = ref(true);
    const inactivepolicy = () => {
      ActivePolicy.value = false;
    };
    const activepolicy = () => {
      ActivePolicy.value = true;
    };
    const isContactSupport = ref(false);
    const contactSupport = () => {
      isContactSupport.value = !isContactSupport.value;
    };

    return {
      goBack,
      showForm,
      showMap,
      goHome,
      ActivePolicy,
      inactivepolicy,
      activepolicy,
      Description,
      Type,
      Domain,
      Rules,
      Country,
      Pincode,
      City,
      Applicable,
      Document,
      FromDate,
      Name,
      isContactSupport,
      contactSupport,
      Owner,
      ToDate
    };
  }
};
</script>

<style lang="scss" scoped>
.btnclass {
  width: 100%;
  height: 48px;
  color: rgba(243, 122, 32, 1);
  border: 1px solid rgba(243, 122, 32, 1);
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));
}
.quarantine_page {
  margin: 13px;
}
.Form {
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));
}
textarea {
  border: none;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 300;
  font-size: 12px;
  line-height: 18px;
  width: 100%;
  color: #000000;
  padding: 5px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));
}
.warningtext {
  font-family: 'SF Pro Text';
  font-style: normal;
  font-weight: 400;
  font-size: 14px;
  line-height: 17px;
  text-align: center;
  letter-spacing: 0.6px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));

  color: #000000;
}
.modal-heading {
  margin: 14px;
  font-size: 20px;
  font-weight: 500;
}
.rect-bar-style {
  padding-left: 45%;
  padding-top: 5px;
}
.option-container {
  // TO DO chat with us button
  //padding: 0 10px 60px;
  .option-head {
    font-weight: 400;
    font-size: 15px;
    padding-bottom: 20px;
  }
  .sf-radio {
    font-size: 15px;
  }
  .sf-button {
    width: -webkit-fill-available;
    border-radius: 5px;
    margin-bottom: 10px;
    height: 48px;
    margin-left: 15px;
    margin-right: 15px;
    text-transform: none;
  }
}
#btn {
  width: 328px;
  height: 48px;
  background: #f37a20;
  border-radius: 4px;
  width: 100%;
  margin-top: 10%;

  label {
    font-weight: 600;
    letter-spacing: 0.8px;
    font-size: 17px;
    color: aliceblue;
  }
}
#btn2 {
  width: 328px;
  height: 48px;
  background: #f37a20;
  border-radius: 4px;
  width: 100%;

  label {
    font-weight: 600;
    letter-spacing: 0.8px;
    font-size: 17px;
    color: aliceblue;
  }
}
.inputbox {
  border: 0;
  outline: 0;
  border-bottom: 0.5px solid rgba(67, 70, 78, 1);
  width: 95%;
  padding: 5px;
  margin: 5px;
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  line-height: 14px;
  color: #37474f;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));
}
.lableclass {
  border: none;

  font-family: 'Poppins';
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */

  color: #000000;
  padding: 4px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));
}
.link {
  border: none;
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  line-height: 14px;
  color: rgba(0, 78, 146, 1);
  padding: 6px;
}

.flexform {
  display: flex;
}

.flexinputbox {
  padding: 5px;
  border: 0;
  outline: 0;
  border: none;
  width: 80%;
  font-size: 13px;
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0)),
    linear-gradient(0deg, rgba(255, 255, 255, 0.55), rgba(255, 255, 255, 0.55));
}
@media (max-width: 388px) {
  .flexform {
    display: flex;
    flex-direction: column;
  }
}

.top-bar {
  padding-right: 32%;
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
</style>
