<template>
  <div class="jumbotron recipes">
    <h1>
      List of all {{recipes.length}} recipes
    </h1>
    <b-list-group>
      <recipe-item v-for="recipe in recipes" :key="recipe._id" :recipe="recipe" @get-ingredients="getIngredients" @get-an-ingredient="getAnIngredient" @delete-ingredient="deleteAnIngredient"></recipe-item>
    </b-list-group>
    <div id="ingredientsOfARecipe"></div>
  </div>
</template>

<script>
import { Api } from '@/Api'
import RecipeItem from '@/components/RecipeItem'

export default {
  name: 'Recipes',
  data() {
    return {
      recipes: []
    }
  },
  mounted() {
    console.log('hi')
    this.getRecipes()
  },
  methods: {
    deleteAnIngredient(id) {
      var ingredientID = prompt('type in the ingredient id')
      Api.delete(`/recipes/${id}/ingredients/${ingredientID}`).then(response => {}).catch(error => {
        this.recipes = []
        console.log(error)
      }).then(() => {
        this.getRecipes()
      })
    },
    getAnIngredient(id) {
      var ingredientID = prompt('type in the ingredient id')
      Api.get(`/recipes/${id}/ingredients/${ingredientID}`).then(response => {
        alert(response.data.name)
      })
    },
    getIngredients(id) {
      Api.get(`/recipes/${id}/ingredients`).then(response => {
        this.ingredients = []
        for (var i = 0; i < response.data.length; i++) {
          this.ingredients[i] = (response.data[i].name)
        }
        console.log(this.ingredients)
        // this.ingredients = response.data
        var div = document.createElement('div')
        div.appendChild(document.createTextNode(this.ingredients))
        document.getElementById('ingredientsOfARecipe').appendChild(div)
      })
    },
    getRecipes() {
      Api.get('/recipes/')
        .then(response => {
          this.recipes = response.data.recipes
          for (var i = 0; i < response.data.recipes.length; i++) {
            console.log(response.data.recipes[i].name)
            console.log('howdy')
          }
        })
        .catch(error => {
          this.recipes = []
          console.log(error)
        })
        .then(() => {
          // This code is always executed (after success or error).
        })
    }
  },
  components: {
    RecipeItem
  }
}

</script>

<style scoped>
  a {
    color: #42b983;
  }
  .recipes {
    margin-bottom: 2em;
  }
</style>
