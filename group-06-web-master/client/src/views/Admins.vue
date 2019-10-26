<template>
  <div>
    <b-container id="secondnavbar">
      <div class="navbar">
      <router-link to="/admins/ingredients" >Ingredients</router-link> |
      <router-link to="/admins/feedbacks" disabled>Feedbacks</router-link>
    </div>
    </b-container>
   <div class="jumbotron admins">
     <b-button class="in-form" @click="postAdmins">Create new Admin</b-button>
     <b-list-group>
      <admin-item  v-for="admin in admins" :key="admin._id" :admin="admin" @delete-admin="deleteAdmins">
      </admin-item>
    </b-list-group>
  </div>
  </div>
</template>container

<script>
import { Api } from '@/Api'
import AdminItem from '@/components/AdminItem'

export default {
  name: 'Admins',
  data() {
    return {
      admins: []
    }
  },
  mounted() {
    this.getAdmins()
  },
  methods: {
    patchAdmin(id) {
      var newName = prompt('type new nam')
      Api.patch(`/admins/${id}`, name, newName).then(response => {
        console.log(response.data.message())
        var index = this.admins.findIndex(admins => admins._id === id)
        this.admins.splice(index, 1)
      }).catch(error => {
        console.log(error)
      }).then(() => {
        this.getAdmins()
      })
    },
    postAdmins() {
      var newAdmin = {
        name: prompt(),
        password: prompt()
      }
      Api.post('/admins/', newAdmin).then(response => {
        this.admins = response.data.admins
      }).catch(error => {
        this.admins = []
        console.log(error)
      }).then(() => {
        this.getAdmins()
      })
    },
    getAdmins() {
      Api.get('/admins/')
        .then(response => {
          this.admins = response.data.admins
        })
        .catch(error => {
          this.admins = []
          console.log(error)
        })
        .then(() => {
          // This code is always executed (after success or error).
        })
    },
    deleteAdmins(id) {
      Api.delete(`/admins/${id}`).then(response => {
        console.log(response.data.message())
        var index = this.admins.findIndex(admins => admins._id === id)
        this.admins.splice(index, 1)
      }).catch(error => {
        console.log(error)
      }).then(() => {
        this.getAdmins()
      })
    }
  },
  components: {
    AdminItem
  }
}

</script>

<style scoped>
  form{
    margin-left: 10%;
    margin-right: 10%;
  }
  .in-form{
    margin-left: 10px;
  }
  .admins {
    margin-bottom: 2em;
  }
  .disabled{
    pointer-events: none;
    cursor: default;
    text-decoration: line-through;
  }
  nav{
    align-content: center;
  }
  #secondnavbar {
    background-color: khaki;
  }
</style>
