<template>
  <div class="jumbotron ingredients">
    <h1>
      List of all {{ingredients.length}} ingredients
    </h1>
    <b-button class="ingredients" @click="createIngredient">Create new Ingredient</b-button>
    <b-button class="ingredients" type="close" @click="deleteAll">delete all</b-button>
    <b-list-group>
      <ingredient-item v-for="ingredient in ingredients" :key="ingredient.name" :ingredient="ingredient" @put-ingredient="putIngredients" @delete-ingredient="deleteIngredients"></ingredient-item>
    </b-list-group>
  </div>
</template>

<script>
import { Api } from '@/Api'
import IngredientItem from '@/components/AdminIngredientItem'

export default {
  name: 'AdminIngredients',
  data() {
    return {
      ingredients: []
    }
  },
  mounted() {
    this.getIngredients()
  },
  methods: {
    deleteAll() {
      Api.delete('/ingredients/').catch(error => {
        console.log(error)
      }).then(() => {
        this.getIngredients()
      })
    },
    createIngredient() {
      var newIngredient = {
        name: prompt('Type in the new ingredient name'),
        description: prompt('Type in the description'),
        nutritionalInfo: prompt('Type in the nutritional into')
      }
      Api.post(`/ingredients/`, newIngredient).then(response => {
        this.ingredients.push(response.data)
      }).catch(error => {
        console.log(error)
      }).then(() => {
        this.getIngredients()
      })
    },
    getIngredients() {
      Api.get('/ingredients/').then(response => {
        this.ingredients = response.data.ingredients
      }).catch(error => {
        this.ingredients = []
        console.log(error)
      }).then(() => {
      // This code is always executed (after success or error).
      })
    },
    putIngredients(id) {
      var updateIngredient = {
        name: prompt('Type in the new ingredient name', ''),
        description: prompt('Type in the new ingredient description', ''),
        nutritionalInfo: prompt('Type in the nutritionalInfo', '')
      }
      Api.put(`/ingredients/${id}`, updateIngredient).then(response => {
        this.ingredients = response.data.ingredients
      }).catch(error => {
        this.ingredients = []
        console.log(error)
      }).then(() => {
        this.getIngredients()
      })
    },
    deleteIngredients(id) {
      Api.delete(`/ingredients/${id}`).then(response => {
        console.log(response.data.message())
        var index = this.ingredients.findIndex(ingredients => ingredients._id === id)
        this.ingredients.splice(index, 1)
      }).catch(error => {
        console.log(error)
      }).then(() => {
        this.getIngredients()
      })
    }
  },
  components: {
    IngredientItem
  }
}

</script>

<style scoped>
  a {
    color: #42b983;
  }
  .ingredients {
    margin-left: 5%;
    margin-right: 5%;
    margin-bottom: 2em;
  }
</style>
