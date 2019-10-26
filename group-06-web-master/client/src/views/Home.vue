<template>
  <div>
    <b-jumbotron header="DIT341 Frontend" lead="Welcome to Your Food Planner">
      <p>Message from the server: {{ message }}</p>
    </b-jumbotron>
    <b-container>
      <b-row>
        <b-col><h1>Responsive BootstrapVue: <a href="https://bootstrap-vue.js.org/docs/components/layout/">Layout and Grid System</a></h1></b-col>
      </b-row>
      <!-- Mobile (< 768px): stack columns by making one half-width and one full-width
           Desktop (>= 768px): keep both columns in same row in 4:8 ratio -->
      <b-row>
        <b-col class="example-col" cols="6" md="4">Message from the server</b-col>
        <b-col class="example-col" cols="12" md="8">{{ message }}</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// @ is an alias to /src
import { Api } from '@/Api'

export default {
  name: 'home',
  data() {
    return {
      message: ''
    }
  },
  mounted() {
    this.getMessage()
  },
  methods: {
    getMessage() {
      Api.get('/')
        .then(response => {
          this.message = response.data.message
        })
        .catch(error => {
          this.message = error
        })
    }
  }
}
</script>

<style>
.example-col {
  padding-top: .75rem;
  padding-bottom: .75rem;
  background-color: whitesmoke;
  border: 2px solid lightgray;
}
</style>
