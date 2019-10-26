<template>
  <b-list-group-item >
    <dl>
      <dt>
        User: {{ user.name}}
      </dt>
    </dl>
    <b-button type="button" class="close"/>
    <b-button type="button" @click="$emit('change-userName', user._id)">change name</b-button>
  </b-list-group-item>
</template>

<script>
export default {
  name: 'users-item',
  props: ['user']
}
</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
