<template>
  <div class="jumbotron feedback">
    <h1>
      Tell us what do you think!
    </h1>
    <h2>
      Write us a feedback
    </h2>
    <div class="smallTextInput">
      <b-form-textarea
        class="smallTextInput"
        id="textarea"
        v-model="name"
        placeholder="Feedback title"
        rows="1"
        max-rows="1"
      ></b-form-textarea>
    </div>
    <div>
      <b-form-textarea
        id="textarea"
        v-model= "text"
        placeholder="Feedback description"
        rows="3"
        max-rows="6"
      ></b-form-textarea>
    </div>
    <b-button type="button" class="createButton" @click="createFeedback()">Send Feedback </b-button>
    <h1 class="secondPart">
      Other users feedback
    </h1>
    <b-list-group>
      <feedback-item v-for="feedback in feedbacks" :key="feedback._id" :feedback="feedback" ></feedback-item>
    </b-list-group>
  </div>
</template>

<script>
import { Api } from '@/Api'
import FeedbackItem from '@/components/FeedbackItem'

export default {
  name: 'Feedbacks',
  data() {
    console.log(Date.UTC(Date.now()))
    console.log('h')
    return {
      feedbacks: [],
      text: '',
      name: ''
    }
  },
  mounted() {
    this.getFeedbacks()
  },
  methods: {
    getFeedbacks() {
      Api.get('/feedbacks/')
        .then(response => {
          this.feedbacks = response.data.feedbacks
        })
        .catch(error => {
          this.feedbacks = []
          console.log(error)
        })
        .then(() => {
          // This code is always executed (after success or error).
        })
    },
    createFeedback() {
      var newFeedback = {
        title: this.name,
        content: this.text,
        date: Date(Date.now())
      }
      Api.post(`/feedbacks/`, newFeedback).then(response => {
        this.feedbacks.push(response.data)
      }).catch(error => {
        console.log(error)
      })
    }
  },
  components: {
    FeedbackItem
  }
}

</script>

<style scoped>
  a {
    color: #42b983;
  }
  .feedback {
    margin-bottom: 2em;
  }
  .smallTextInput{
    margin-right: 50%;
    height: 40px;
  }
  #textarea {
    margin-top: 5px;
    margin-bottom: 5px;
  }
  .secondPart{
    margin-top: 40px;
  }
</style>
