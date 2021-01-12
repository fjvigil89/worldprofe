'use strict';

var browserSync = require('browser-sync'),
    del = require('del'),
    es = require('event-stream'),
    gulp = require('gulp'),
    angularFilesort = require('gulp-angular-filesort'),
    templateCache = require('gulp-angular-templatecache'),
    changed = require('gulp-changed'),
    expect = require('gulp-expect-file'),
    flatten = require('gulp-flatten'),
    htmlmin = require('gulp-htmlmin'),
    imagemin = require('gulp-imagemin'),
    ngConstant = require('gulp-ng-constant'),
    rename = require('gulp-rename'),
    inject = require('gulp-inject'),
    plumber = require('gulp-plumber'),
    rev = require('gulp-rev'),
    sass = require('gulp-sass'),
    runSequence = require('run-sequence'),
    yaml = require('gulp-yaml'),
    uglify = require('gulp-uglify'),
    filelist = require('gulp-filelist'),
    replace = require('gulp-replace'),
    wiredep = require('wiredep').stream;

var handleErrors = require('./gulp/handleErrors');
var config = require('./gulp/config');
var serve = require('./gulp/serve');
var build = require('./gulp/build');

gulp.task('copy', function () {
    return es.merge(
        gulp.src(config.app + 'i18n/**')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.dist + 'i18n/'))
            .pipe(gulp.dest(config.dist + 'i18n/')),
        gulp.src(config.fontsDir + '/**/*.{woff,woff2,svg,ttf,eot,otf}')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.dist + 'assets/fonts/'))
            .pipe(rev())
            .pipe(gulp.dest(config.dist + 'assets/fonts/'))
            .pipe(rev.manifest(config.revManifest, {
                base: config.dist,
                merge: true
            }))
            .pipe(gulp.dest(config.dist)),
        // JavaScript estático
        gulp.src(config.jsDir + '/**')
            .pipe(uglify())
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(gulp.dest(config.dist + 'assets/js/')),
        // CSS estático
        gulp.src(config.staticCssDir + '/**')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(gulp.dest(config.dist + 'assets/staticCss/')),
        gulp.src([config.app + 'robots.txt', config.app + 'favicon.ico', config.app + '.htaccess'], {dot: true})
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.dist))
            .pipe(gulp.dest(config.dist)),
        gulp.src(config.menuDir + "**")
            .pipe(gulp.dest(config.dist + "menu/"))
    );
});

gulp.task('html', function () {
    return gulp.src(config.app + 'app/**/*.html')
        .pipe(htmlmin({collapseWhitespace: false}))
        .pipe(templateCache({
            module: 'app',
            root: 'app/',
            moduleSystem: 'IIFE'
        }))
        .pipe(gulp.dest(config.tmp));
});

gulp.task('images', function () {
    return es.merge(gulp.src(config.app + 'assets/images/**')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.dist + 'assets/images'))
            .pipe(imagemin({optimizationLevel: 5, progressive: true, interlaced: true}))
            .pipe(rev())
            .pipe(gulp.dest(config.dist + 'assets/images'))
            .pipe(rev.manifest(config.revManifest, {
                base: config.dist,
                merge: true
            }))
            .pipe(gulp.dest(config.dist))
            .pipe(browserSync.reload({stream: true})),
        gulp.src(config.app + 'assets/images/static/**')
            .pipe(imagemin({optimizationLevel: 5, progressive: true, interlaced: true}))
            .pipe(gulp.dest(config.dist + 'assets/images/static'))
    );
});

gulp.task('inject', function () {
    return gulp.src(config.app + 'index.html')
        .pipe(inject(es.merge(
            gulp.src(config.app + 'app/**/*.css', {read: false}),
            gulp.src(config.app + 'app/**/*.js').pipe(angularFilesort())
        ), {relative: true}))
        .pipe(gulp.dest(config.app));
});

gulp.task('menu', function () {
    return gulp.src(config.menuDir + '*.yaml')
        .pipe(yaml({schema: 'DEFAULT_SAFE_SCHEMA'}))
        .pipe(gulp.dest(config.menuDir));
});

gulp.task('sass', function () {
    return es.merge(
        gulp.src(config.sassSrc)
            .pipe(plumber({errorHandler: handleErrors}))
            // to reload everything every time
            // .pipe(expect(config.sassSrc))
            // .pipe(changed(config.cssDir, {extension: '.css'}))
            .pipe(sass({includePaths: config.bower}).on('error', sass.logError))
            .pipe(gulp.dest(config.cssDir)),
        gulp.src([config.bower + '**/fonts/**/*.{woff,woff2,svg,ttf,eot,otf}'])
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.fontsDir))
            .pipe(flatten())
            .pipe(gulp.dest(config.fontsDir))
    );
});

gulp.task('styles', ['sass'], function () {
    return gulp.src(config.app + 'assets/css')
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('watch', function () {
    gulp.watch('bower.json', ['install']);
    gulp.watch(['gulpfile.js', 'package.json'], ['ngconstant:dev']);
    gulp.watch(config.sassSrc, ['styles']);
    gulp.watch(config.app + 'app/**/*.js', ['inject']);
    gulp.watch(config.menuDir + '*.yaml', ['menu']);
    gulp.watch([
        config.app + 'index.html',
        config.app + 'app/**/*.html',
        config.app + 'i18n/**',
        config.app + 'app/**/*.json',
        config.app + 'app/**/*.css'
    ]).on('change', browserSync.reload);
});

gulp.task('wiredep', ['wiredep:app']);

gulp.task('wiredep:app', function () {
    var stream = gulp.src(config.app + 'index.html')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(wiredep({
            exclude: [
                /angular-i18n/,  // localizations are loaded dynamically
                'bower_components/bootstrap-sass/assets/javascripts/' // Exclude Bootstrap js files as we use ui-bootstrap
            ]
        }))
        .pipe(gulp.dest(config.app));

    return es.merge(stream, gulp.src(config.sassSrc)
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(wiredep({
            ignorePath: /\.\.\/bower_components\// // remove ../webapp/bower_components/ from paths of injected sass files
        }))
        .pipe(gulp.dest(config.scss)));
});

gulp.task('ngconstant:dev', function () {
    return ngConstant({
        name: 'app',
        constants: {
            DEBUG_INFO_ENABLED: true
        },
        template: config.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.js'))
        .pipe(gulp.dest(config.app + 'app/'));
});

gulp.task('ngconstant:prod', function () {
    return ngConstant({
        name: 'app',
        constants: {
            DEBUG_INFO_ENABLED: false
        },
        template: config.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.js'))
        .pipe(gulp.dest(config.app + 'app/'));
});

gulp.task('assets:prod', ['images', 'styles', 'html'], build);

gulp.task('clean', function () {
    return del([config.dist, config.tmp], {dot: true});
});

gulp.task('build', ['clean'], function (cb) {
    runSequence(['copy', 'wiredep', 'menu', 'ngconstant:prod'], 'inject', 'assets:prod', cb);
});

gulp.task('install', function () {
    runSequence(['wiredep', 'sass', 'menu'], 'ngconstant:dev', 'inject');
});

gulp.task('serve', function () {
    runSequence('install', serve);
});
