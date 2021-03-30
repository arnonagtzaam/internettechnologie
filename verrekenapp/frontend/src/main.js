import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import ApiService from "./services/apiService";



Vue.config.productionTip = false
Vue.prototype.$http = ApiService;
Vue.prototype.$http.init();

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
