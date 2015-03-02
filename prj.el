;;; prj.el --- Project Configuration

;;; Commentary:
;; JDEE Project
;; Author: Zhong Lizhi <zlz.3907 <at> gmail.com>
;; Version: 1.0

;;; Code:
(defvar prj-ant-buildfile "build.xml"
  "Specify a build file name.")
(defvar prj-working-directory
  (file-name-directory (jde-find-project-file (file-truename ".")))
  "Specify working directory.")
(defvar prj-ant-buildfile-abspath (concat prj-working-directory
                                          prj-ant-buildfile)
  "Abslute path of build file.")
(defvar prj-customize-name ""
  "Specify the project name, by default is current directory name.")
(defvar prj-name
  (if (eq "" prj-customize-name)
      (file-name-base prj-working-directory))
  "Specify the project name.")
(defvar prj-sourcepath
  (quote ((concat prj-working-directory "src")
          (concat prj-working-directory "test/unit")
          (concat prj-working-directory "test/verify")
          (concat prj-working-directory "test/integration")))
  "Specify source path.")
(defvar prj-classpath
  (quote ((concat prj-working-directory "lib")
          (concat prj-working-directory "build/main")
          (concat prj-working-directory "build/test")))
  "Specify classpath.")

(jde-project-file-version "1.0")
(jde-set-variables
 ;; global
 '(jde-project-name prj-name)
 '(jde-run-working-directory prj-working-directory)
 ;;'(jde-junit-working-directory prj-working-directory)
 '(jde-ant-working-directory prj-working-directory)
 ;; '(jde-compile-option-encoding "utf-8")
 '(jde-ant-read-target t)
 '(jde-ant-enable-find t)
 ;;'(jde-ant-buildfile prj-ant-buildfile-abspath)
 '(jde-build-function 'prj-build)
 ;; classpath
 '(jde-global-classpath prj-classpath)
 '(jde-compile-option-sourcepath prj-sourcepath)
 '(jde-compile-option-classpath prj-classpath)
 ;;'(jde-compile-option-directory "build/main")
 '(Jde-sourcepath prj-sourcepath)
 '(jde-ant-build-classpath prj-classpath))

;;; prj.el ends here
