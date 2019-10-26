<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/ingredients">Ingredients</router-link> |
      <router-link to="/recipes">Recipes</router-link> |
      <router-link to="/users">Users</router-link> |
      <router-link to="/feedbacks">Feedbacks</router-link> |
      <router-link to="/admins">Admins</router-link> |
      <router-link to="/weeklyPlans">Weekly Plans</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
  background-color: #555;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
