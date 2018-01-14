<template>
  <div id="app">
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
      <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
          <span class="mdl-layout-title">SES Templates</span>
          <div class="mdl-layout-spacer"></div>
          <!-- <nav class="mdl-navigation mdl-layout--large-screen-only">
            <a class="mdl-navigation__link" href="">Link</a>
            <a class="mdl-navigation__link" href="">Link</a>
            <a class="mdl-navigation__link" href="">Link</a>
            <a class="mdl-navigation__link" href="">Link</a>
          </nav> -->
        </div>
      </header>
      <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">Menu</span>
        <nav class="mdl-navigation">
          <a class="mdl-navigation__link" href="">Link</a>
          <a class="mdl-navigation__link" href="">Link</a>
          <a class="mdl-navigation__link" href="">Link</a>
          <a class="mdl-navigation__link" href="">Link</a>
        </nav>
      </div>
      <main class="mdl-layout__content">
        <div class="page-content">
          <router-view/>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  .mdl-layout__header,
  .mdl-layout__header .mdl-layout__drawer-button {
    color: rgb(255,255,255);
  }

  .mdl-layout__title,
  .mdl-layout-title {
    font-weight: bold;
  }

  body {
    background-color: rgb(255,255,255);
  }
</style>
