import Vue from 'vue';
import VueCompositionAPI, { reactive, computed } from '@vue/composition-api';

// We need to register it again because of Vue instance instantiation issues
Vue.use(VueCompositionAPI);

const state = reactive({
  isCartSidebarOpen: false,
  isWishlistSidebarOpen: false,
  isLoginModalOpen: false,
  isCategoryGridView: true,
  isFilterSidebarOpen: false,
  isSearchOpen: false,
  searchString: '',
  IsSearchVisible: true,
  selectedLocation: {
    latitude: '',
    longitude: '',
    address: ''
  },
  clearCartPopup: false,
  enableLoadindBar: false,
  explorePageData: null,
  sLocation: {
    lat: '',
    long: '',
    addres: ''
  },
  dLocation: {
    late: '',
    lng: '',
    addresss: ''
  },
  Name: '',
  phoneNo: '',
  trackLat: '',
  trackLong: '',
  quoteData: '',
  TransactionId: '',
  cartItem: '',
  token: '',
  confirmDatas: '',
  confirmDataContext: '',
  cartData: '',
  initResult: '',
  experienceId: ''
});

const useUiState = () => {
  const initResult = computed(() => state.initResult);
  const setinitResult = (data) => (state.initResult = data);
  const cartData = computed(() => {
    state.cartData;
  });
  const setcartData = (data) => {
    state.cartData = data;
  };

  const confirmDataContext = computed(() => state.confirmDataContext);
  const setconfirmDataContext = (data) => (state.confirmDataContext = data);

  const confirmDatas = computed(() => state.confirmDatas);
  const setconfirmData = (data) => (state.confirmDatas = data);
  const token = computed(() => state.token);
  const settoken = (data) => {
    state.token = data;
  };

  const cartItem = computed(() => state.cartItem);
  const setcartItem = (data) => (state.cartItem = data);
  const TransactionId = computed(() => state.TransactionId);
  const setTransactionId = (data) => {
    state.TransactionId = data;
  };

  const quoteData = computed(() => state.quoteData);
  const setquoteData = (data) => (state.quoteData = data);

  const trackLat = computed(() => state.trackLat);
  const trackLong = computed(() => state.trackLong);
  const settrackLat = (data) => {
    state.trackLat = data;
  };
  const settrackLong = (data) => {
    state.trackLong = data;
  };

  const Name = computed(() => state.Name);
  const setName = (data) => {
    state.Name = data;
  };
  const phoneNo = computed(() => {
    state.phoneNo;
  });
  const setphoneNo = (data) => {
    state.phoneNo = data;
  };

  const sLocation = computed(() => state.sLocation);
  const updatesLocation = (location) => {
    state.sLocation = location;
  };
  const dLocation = computed(() => state.dLocation);
  const updatedLocation = (location) => {
    state.dLocation = location;
  };
  const isCartSidebarOpen = computed(() => state.isCartSidebarOpen);
  const toggleCartSidebar = () => {
    state.isCartSidebarOpen = !state.isCartSidebarOpen;
  };

  const isWishlistSidebarOpen = computed(() => state.isWishlistSidebarOpen);
  const toggleWishlistSidebar = () => {
    state.isWishlistSidebarOpen = !state.isWishlistSidebarOpen;
  };

  const isLoginModalOpen = computed(() => state.isLoginModalOpen);
  const toggleLoginModal = () => {
    state.isLoginModalOpen = !state.isLoginModalOpen;
  };

  const isCategoryGridView = computed(() => state.isCategoryGridView);
  const changeToCategoryGridView = () => {
    state.isCategoryGridView = true;
  };
  const changeToCategoryListView = () => {
    state.isCategoryGridView = false;
  };

  const isFilterSidebarOpen = computed(() => state.isFilterSidebarOpen);
  const toggleFilterSidebar = () => {
    state.isFilterSidebarOpen = !state.isFilterSidebarOpen;
  };

  const isSearchOpen = computed(() => state.isSearchOpen);
  const toggleSearch = (value = !state.isSearchOpen) => {
    state.isSearchOpen = value;
  };

  const searchString = computed(() => state.searchString);
  const changeSearchString = (data) => {
    state.searchString = data;
  };

  const IsSearchVisible = computed(() => state.IsSearchVisible);
  const toggleSearchVisible = (value = !state.IsSearchVisible) => {
    state.IsSearchVisible = value;
  };

  const selectedLocation = computed(() => state.selectedLocation);
  const updateLocation = (location) => {
    state.selectedLocation = location;
  };

  const clearCartPopup = computed(() => state.clearCartPopup);
  const changeClearCart = (value: boolean) => {
    state.clearCartPopup = value;
  };

  const enableLoadindBar = computed(() => state.enableLoadindBar);
  const toggleLoadindBar = (value = !state.enableLoadindBar) => {
    state.enableLoadindBar = value;
  };

  const explorePageData = computed(() => state.explorePageData);
  const updateExpPageData = (data) => {
    state.explorePageData = data;
  };

  const experienceId = computed(() => state.experienceId);
  const setExperienceId = (id:string) => {
    state.experienceId = id;
  };

  return {
    isCartSidebarOpen,
    isWishlistSidebarOpen,
    isLoginModalOpen,
    isCategoryGridView,
    isFilterSidebarOpen,
    isSearchOpen,
    searchString,
    IsSearchVisible,
    selectedLocation,
    clearCartPopup,
    enableLoadindBar,
    explorePageData,
    toggleCartSidebar,
    toggleWishlistSidebar,
    toggleLoginModal,
    changeToCategoryGridView,
    changeToCategoryListView,
    toggleFilterSidebar,
    toggleSearch,
    changeSearchString,
    toggleSearchVisible,
    updateLocation,
    changeClearCart,
    toggleLoadindBar,
    updateExpPageData,
    sLocation,
    updatesLocation,
    dLocation,
    updatedLocation,
    Name,
    setName,
    phoneNo,
    setphoneNo,
    trackLat,
    trackLong,
    settrackLong,
    settrackLat,
    quoteData,
    setquoteData,
    TransactionId,
    setTransactionId,
    cartItem,
    setcartItem,
    token,
    settoken,
    confirmDatas,
    setconfirmData,
    confirmDataContext,
    setconfirmDataContext,
    cartData,
    setcartData,
    initResult,
    setinitResult,
    experienceId,
    setExperienceId
  };
};

export default useUiState;
