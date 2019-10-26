<template>
  <b-list-group-item >
    <dl>
      <dt>
        Recipe: {{ recipe.name}}
      </dt>
      <dt>
        Ingredients: {{recipe.ingredients}}
      </dt>
      <dt>
        <b-button @click="$emit('get-ingredients', recipe._id)">get Ingredients</b-button>
        <b-button @click="$emit('get-an-ingredient', recipe._id)">get an ingredient</b-button>
        <b-button @click="$emit('delete-ingredient', recipe._id)">delete an ingredient</b-button>
      </dt>
    </dl>
    <b-button type="button" class="close"/>
  </b-list-group-item>
</template>

<script>
export default {
  name: 'recipes-item',
  props: ['recipe']
}
</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
