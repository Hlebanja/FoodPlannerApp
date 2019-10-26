<template>
  <b-list-group-item >
    <dl>
      <dt>
        Ingredient: {{ ingredient.name}}
      </dt>
      <dt>
        Type: {{ingredient.description}}
      </dt>
      <dt>
        Nutritional Info: {{ingredient.nutritionalInfo}}
      </dt>
      <dt>
        <b-button type="button" class="put" @click="$emit('put-ingredient', ingredient._id)">update</b-button>
      </dt>
      <dt>
        <b-button type="button" class="close" @click="$emit('delete-ingredient', ingredient._id)">&times;</b-button>
      </dt>
    </dl>
  </b-list-group-item>
</template>

<script>
import IngredientItem from '@/components/IngredientItem'
export default {
  name: 'ingredients-item',
  props: ['ingredient'],
  components: IngredientItem
}

</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
