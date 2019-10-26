<template>
  <div class="jumbotron ingredients">
    <h1>
      List of all {{ingredients.length}} ingredients
    </h1>
    <b-list-group >
      <ingredient-item class="ingredients" v-for="ingredient in ingredients" :key="ingredient.name" :ingredient="ingredient"></ingredient-item>
    </b-list-group>
  </div>
</template>

<script>
import { Api } from '@/Api'
import IngredientItem from '@/components/IngredientItem'

export default {
  name: 'Ingredients',
  data() {
    return {
      ingredients: [],
      text: ''
    }
  },
  mounted() {
    this.getIngredients()
  },
  methods: {
    getIngredients() {
      Api.get('/ingredients/')
        .then(response => {
          this.ingredients = response.data.ingredients
          // for (var i = 0; i < response.data.ingredients.length; i++) {
          //   console.log(response.data.ingredients[i].name)
          //   console.log('howdy')
          // }
        })
        .catch(error => {
          this.ingredients = []
          console.log(error)
        })
        .then(() => {
          // This code is always executed (after success or error).
        })
    }
  //   deleteIngredients(id) {
  //     Api.delete(`/ingredients/${id}`).then(response => {
  //       console.log(response.data.message())
  //       var index = this.ingredients.findIndex(ingredients => ingredients._id === id)
  //       this.ingredients.splice(index, 1)
  //     }).catch(error => {
  //       console.log(error)
  //     }).then(() => {
  //       this.getIngredients()
  //     })
  //   }
  // },
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
    margin-bottom: 2em;
  }
</style>
