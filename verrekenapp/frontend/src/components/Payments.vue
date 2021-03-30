<template>
    <div class="payments-container">
        <h2 class="payments-title">Wie heeft wat betaald</h2>
        <el-table :data="formattedPayments" :show-header="false" height="22vh" empty-text="Nog geen uitgaven">
            <el-table-column prop="payment"></el-table-column>
        </el-table>
        <h3>Totaal is €{{totalAmount}} uitgegeven</h3>
    </div>
</template>

<script>
export default {
    name: 'Payments',
    props: {
        formattedPayments: Array,
        totalAmount: Number
    }
}
</script>
