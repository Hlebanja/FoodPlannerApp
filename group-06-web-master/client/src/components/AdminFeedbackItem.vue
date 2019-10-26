<template>
  <b-list-group-item >
    <dl>
      <dt>
        Feedback: {{feedback.content}}
      </dt>
      <dt>
        <b-button type="button" class="delete" @click="$emit('delete-feedback', feedback._id)">&times;</b-button>
      </dt>
    </dl>
  </b-list-group-item>
</template>

<script>
import FeedbackItem from '@/components/FeedbackItem'
export default {
  name: 'feedbacks-item',
  props: ['feedback'],
  comments: {
    FeedbackItem
  }
}
</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
