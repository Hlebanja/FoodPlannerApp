<template>
  <b-list-group-item >
    <dl>
      <dt>
        Feedback: {{feedback.content}}
      </dt>
    </dl>
  </b-list-group-item>
</template>

<script>
export default {
  name: 'feedbacks-item',
  props: ['feedback']
}
</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
