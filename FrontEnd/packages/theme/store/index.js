import { useUiState } from '~/composables';

const {settoken}=useUiState()


const actions = {
  async onAuthStateChangedAction(state, { authUser }) {
    if (!authUser) {
      // remove state
      state.commit('SET_USER', null);

      // redirect from here
      this.$router.push({
        path: '/Login'
      });
    } else {
      const { uid, email, Aa } = authUser;


      settoken(Aa)

      //localStorage.setItem('token', Aa);
      state.commit('SET_USER', {
        uid,
        email,
        Aa
      });
      this.$router.push({
        path: '/'
      });
    }
  }
};

const mutations = {
  SET_USER(state, user) {
    state.user = user;
  }
};

const state = () => ({
  user: null
});

const getters = {
  getUser(state) {
    return state.user;
  }
};

export default {
  state,
  actions,
  mutations,
  getters
};
