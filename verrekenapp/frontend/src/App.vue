<template>
  <div id="app">
    <el-header class="page-header">
      <h1 class="header-title">Wie betaalt wie</h1>
    </el-header>
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'app',
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
}

.page-header {
  background-color: #409EFF;
  color: #333;
  line-height: 60px;
  text-align: left;
}

.header-title {
  margin: 0;
  color: white;
}
</style>
