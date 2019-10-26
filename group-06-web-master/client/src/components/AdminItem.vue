<template>
  <b-list-group-item >
    <dl>
      <dt>
        Name: {{admin.name}}
      </dt>
      <dt>
        <b-button type="button" class="close" @click="$emit('delete-admin', admin._id)">&times;</b-button>
      </dt>
      <dt>
        <b-button type="button" class="close"/>
      </dt>
    </dl>
  </b-list-group-item>
</template>

<script>
export default {
  name: 'admin-item',
  props: ['admin']
}
</script>

<style scoped>
  img {
    width: 33px;
    margin-right: 10px;
  }
</style>
