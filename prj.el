;;; prj.el --- TextLineProcessor project

;;; Commentary:
;; JDEE Project
;; Author: Zhong Lizhi <zlz.3907 <at> gmail.com>
;; Version: 1.0

;;; Code:
(jde-project-file-version "1.0")
(defvar project-base "~/projects/textlineprocessor" "The project base path.")
(jde-set-variables
 '(jde-project-name "textlineprocessor")
 ;; 运行时使用
 '(jde-run-option-classpath
   (quote
    ("~/projects/textlineprocessor/lib/*"
     "~/projects/textlineprocessor/build/main"
     "~/projects/textlineprocessor/build/test"
     )))
 ;; 调试时使用
 '(jde-db-option-classpath
   (quote
    ("~/projects/textlineprocessor/lib/*"
     "~/projects/textlineprocessor/build/main"
     "~/projects/textlineprocessor/build/test"
     )))
 ;; 在编译时用到
 '(jde-compile-option-sourcepath
   (quote ("~/projects/textlineprocessor/src"
           "~/projects/textlineprocessor/test/unit"
           "~/projects/textlineprocessor/test/verify"
           "~/projects/textlineprocessor/test/integration")))
 '(jde-compile-option-classpath
   (quote ("~/projects/textlineprocessor/lib/*"
           "~/projects/textlineprocessor/build/main"
           "~/projects/textlineprocessor/build/test"
           )))
 ;; Junit
 '(jde-junit-working-directory "~/projects/textlineprocessor/")
 '(jde-run-working-directory "~/projects/textlineprocessor/")
 ;; '(jde-sourcepath
 ;;   (quote ("~/projects/textlineprocessor/src/"
 ;;           "~/projects/textlineprocessor/test/unit"
 ;;           "~/projects/textlineprocessor/test/verify"
 ;;           "~/projects/textlineprocessor/test/integration")))
 ;;'(jde-run-application-class "~/projects/textlineprocessor/bin")
 ;;'(jde-run-working-directory "e:/home/lizhi/workspace/2012/ztools")
 '(jde-compile-option-directory "~/projects/textlineprocessor/build/main/")
 '(jde-compile-option-encoding "utf-8")
 '(jde-build-function (quote (jde-ant-build)))
 '(jde-ant-enable-find t)
 '(jde-ant-read-target t)
 '(jde-ant-home "~/apache/ant")
 '(jde-ant-invocation-method (quote ("Ant Server")))
 )

;;; prj.el ends here
