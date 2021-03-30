<template>
  <div class="input-fields">
    <el-form  label-position="top" :model="form" label-width="100px" :rules="rules" ref="ruleForm" class="input-form">
      <el-form-item label="Naam" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="Bedrag" prop="amount" >
        <el-input v-model="form.amount"><template slot="prepend">€</template></el-input>
      </el-form-item>
      <div class="buttons">
        <el-button @click="submitForm('ruleForm')" type="primary" icon="el-icon-check" circle></el-button>
        <el-button @click="reset('ruleForm')" type="primary" icon="el-icon-refresh-right" circle></el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'InputFields',
  data () {
    return {
      form: {
        name: "",
        amount: ""
      },

      rules: {
        name: [{
          required: true,
          message: 'Voer een naam in',
          trigger: 'blur'
        }, { 
          pattern: /^[a-zA-Z]+$/,
          message: 'Naam mag alleen letters bevatten',
          trigger: 'blur' }
        ],
        amount: [{
          required: true,
          message: 'Voer een bedrag in',
          trigger: 'blur'
        }, {
          pattern: /^\d+(?:[.|,]\d{0,2})?$/,
          message: 'Bedrag mag alleen getallen bevatten',
          trigger: 'blur'
        }]
      },
    }
  },

  methods: {
    apitest() {
      this.$http.get("total");
    },
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$emit('clicked', this.form);
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    reset(formName) {
      this.form.name = "";
      this.form.amount = "";
      this.$refs[formName].fields.find((f) => f.prop == "name").resetField();
      this.$refs[formName].fields.find((f) => f.prop == "amount").resetField();
      this.$emit('clicked', "reset");
    }  
  }
}
</script>
<style scoped>
.input-fields {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.input-form {
  text-align: left;
  width: 85%;
}

.buttons {
  display: flex;
  justify-content:space-evenly;
}

.el-form--label-top {
  padding: 0;
}
</style>
