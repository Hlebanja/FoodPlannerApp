<template>
  <div class="jumbotron weeklyPlans">
    <h1>
      List of all {{weeklyPlans.length}} Weekly Plans
    </h1>
    <b-list-group>
      <weeklyPlan-item v-for="weeklyPlan in weeklyPlans" :key="weeklyPlan.name" :weeklyPlan="weeklyPlan" ></weeklyPlan-item>
    </b-list-group>
  </div>
</template>

<script>
import { Api } from '@/Api'
import WeeklyPlanItem from '@/components/WeeklyPlanItem'

export default {
  name: 'WeeklyPlans',
  data() {
    return {
      weeklyPlans: []
    }
  },
  mounted() {
    this.getWeeklyPlans()
  },
  methods: {
    getWeeklyPlans() {
      Api.get('/weeklyPlans/')
        .then(response => {
          this.weeklyPlans = response.data.weeklyPlans
        })
        .catch(error => {
          this.weeklyPlans = []
          console.log(error)
        })
        .then(() => {
          // This code is always executed (after success or error).
        })
    }
  },
  components: {
    WeeklyPlanItem
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
