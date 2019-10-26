import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Ingredients from './views/Ingredients.vue'
import Recipes from './views/Recipes.vue'
import Users from './views/Users.vue'
import Feedbacks from './views/Feedbacks.vue'
import Admins from './views/Admins.vue'
import WeeklyPlans from './views/WeeklyPlans.vue'
import AdminIngredients from './views/AdminIngredients'
import AdminFeedbacks from './views/AdminFeedbacks'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/ingredients',
      name: 'ingredients',
      component: Ingredients
    },
    {
      path: '/recipes',
      name: 'recipes',
      component: Recipes,
      // child: Ingredients
      children: [ {
        // UserProfile will be rendered inside User's <router-view>
        // when /user/:id/profile is matched
        path: '/ingredients',
        component: Ingredients,
        name: 'ingredients'
      }]
    },
    {
      path: '/users',
      name: 'users',
      component: Users
    },
    {
      path: '/feedbacks',
      name: 'feedbacks',
      component: Feedbacks
    },
    {
      path: '/admins',
      name: 'admins',
      component: Admins
    },
    {
      path: '/weeklyPlans',
      name: 'weeklyPlans',
      component: WeeklyPlans
    },
    {
      path: '/admins/ingredients',
      name: 'admin-ingredients',
      component: AdminIngredients
    },
    {
      path: '/admins/feedbacks',
      name: 'admin-feedbacks',
      component: AdminFeedbacks
    }
  ]
})
