<template>
  <b-list-group-item >
    <dl>
      <dt>
        Weekly Plan: {{ weeklyPlan.days }}
      </dt>
    </dl>
    <b-button type="button" class="close"/>
  </b-list-group-item>
</template>

<script>
export default {
  name: 'weeklyPlans-item',
  props: ['weeklyPlan']
}
</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
