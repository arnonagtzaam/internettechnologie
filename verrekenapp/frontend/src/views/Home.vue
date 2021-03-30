<template>
  <div class="home">
    <InputFields @clicked="createPayment"/>
    <Payments :formattedPayments="payments.formattedPayments" :totalAmount="payments.totalAmount"/>
    <Settlements :settlement="settlements.settlement"/>
  </div>
</template>

<script>
import InputFields from '@/components/InputFields.vue'
import Payments from '@/components/Payments.vue'
import Settlements from '@/components/Settlements.vue'

export default {
  name: 'Home',
  components: {
    InputFields,
    Payments,
    Settlements
  },

  data() {
    return {
      payments: {
        payments: [],
        formattedPayments: [],
        totalAmount: 0
      },
      settlements: {
        settlement: []
      }
    }
  },

  methods: {
    async addPayment(payment) {
      await this.$http.post("payments/add", payment).then(response => {
          console.log(response.data)
          this.payments.payments.push(response.data);
          this.payments.formattedPayments.push({ payment: response.data.name + " heeft €" + response.data.amount + " betaald"});
        
      });

      this.countTotal();
      this.settlements.settlement = [];
      await this.$http.post("total-payment", this.payments.payments).then(response => {
        for (var i = 0; i < response.data.loans.length; i++) {
          this.settlements.settlement.push({ payment: response.data.loans[i].from + " betaald " + response.data.loans[i].to + " €" + response.data.loans[i].amount });
        }
      });
    },

    createPayment(value) {
      if(value == "reset"){
        console.log("OKE");
        this.payments.payments = [];
        this.payments.formattedPayments = [];
        this.payments.totalAmount = 0;
        this.settlements.settlement = [];
      } else  {
        console.log(value);
        value.amount = value.amount.replace(",",".")
        this.addPayment(value);
      }
    },

    countTotal() {
      this.payments.totalAmount = 0;
      for (var i = 0; i < this.payments.payments.length; i++) {
        this.payments.totalAmount += this.payments.payments[i].amount;
      }
      this.payments.totalAmount = Math.round(this.payments.totalAmount * 100) / 100;
    },
  },
}
</script>
