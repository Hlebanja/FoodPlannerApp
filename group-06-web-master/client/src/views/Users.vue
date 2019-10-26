<template>
  <div class="jumbotron users">
    <h1>
      List of all {{users.length}} users
    </h1>
    <b-button type="button" class="createButton" @click="createUser()">Create User</b-button>
    <b-list-group>
      <user-item v-for="user in users" :key="user._id" :user="user" @change-userName="patchName"></user-item>
    </b-list-group>
  </div>
</template>

<script>
import { Api } from '@/Api'
import UserItem from '@/components/UserItem'

export default {
  name: 'Users',
  data() {
    return {
      users: []
    }
  },
  mounted() {
    this.getUsers()
  },
  methods: {
    patchName(id) {
      var newName = prompt('type the new name')
      Api.patch(`/users/${id}`, {
        name: newName
      }).then(response => {
        console.log(response.data)
      })
    },
    getUsers() {
      Api.get('/users/')
        .then(response => {
          this.users = response.data.users
        })
        .catch(error => {
          this.users = []
          console.log(error)
        })
        .then(() => {
          // This code is always executed (after success or error).
        })
    },
    createUser() {
      var newUser = {
        name: prompt('Type in the new user name', 'Adam'),
        password: prompt('Type in the user password', 'Thaigo')
      }
      Api.post(`/users/`, newUser).then(response => {
        this.users.push(response.data)
      }).catch(error => {
        console.log(error)
      }).then(() => {
        this.getUsers()
      })
    }
  },
  components: {
    UserItem
  }
}

</script>

<style scoped>
  a {
    color: #42b983;
  }
  .users {
    margin-bottom: 2em;
  }

</style>
