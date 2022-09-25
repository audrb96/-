<template>
  <div>
    <header>
      <h1><em>Tagify</em> Vue Demo:</h1>
    </header>
    <Tags
      ref="myRef"
      :settings="tagifyStuff.tagifySettings"
      :suggestions="tagifyStuff.suggestions"
      :value="tagifyStuff.value"
      :on-change="onTagsChange"
    />
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
// import Tags from "@yaireo/tagify/dist/tagify.vue"
import Tags from '@/components/TagifyInput'

export default {
  name: 'App',
  components: { Tags },

  setup(props) {
    const myRef = ref(null)

    const tagifyStuff = reactive({
      value: 'a,b,c',

      tagifySettings: {
        whitelist: [],
        dropdown: {
          enabled: 0,
        },
        callbacks: {
          add(e) {
            // console.log("tag added:", e.detail);
          },
        },
      },

      suggestions: [],
    })

    function onTagsChange(e) {
      //   console.log('tags changed:', e.target.value)
    }

    return {
      onTagsChange,
      myRef,
      tagifyStuff,
    }
  },
}
</script>

<style>
html,
body,
#app {
  height: 100%;
  padding: 0;
  margin: 0;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font: 16px Arial;
  background: #f5f5f5;
}

.tagify {
  margin: 2em;
}

header {
  color: white;
  background: #111;
  background: linear-gradient(145deg, #a20838 0%, #36144b 100%);
  text-align: center;
  margin-bottom: 2em;
  border-bottom: 1px solid #ccc;
}
header h1 {
  margin: 0;
  padding: 1.2em 0;
}
header ~ * {
  margin: 1em;
}
</style>
