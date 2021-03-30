<template>
    <div class="settlements-container">
        <h2 class="settlements-title">Verrekeningen</h2>
        <el-table :data="settlement" :show-header="false" height="23vh" empty-text="Nog geen uitgaven">
            <el-table-column prop="payment"></el-table-column>
        </el-table>
    </div>
</template>
<script>
export default {
    name: 'Settlements',

    props: {
        settlement: Array
    },
}
</script>
