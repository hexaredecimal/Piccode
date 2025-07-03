(require 'subr-x)

(defvar piccode-mode-syntax-table
  (let ((table (make-syntax-table)))
    ;; C/C++ style comments
    (modify-syntax-entry ?/ ". 124b" table)
    (modify-syntax-entry ?* ". 23" table)
    (modify-syntax-entry ?\n "> b" table)
    ;; Preprocessor stuff?
    (modify-syntax-entry ?# "." table)
    ;; Chars are the same as strings
    (modify-syntax-entry ?' "\"" table)
    table))

(defun piccode-constants ()
  '("true" "false" "await"))

(defun piccode-keywords ()
  '("import" "module" "if" "else" "when" "is" "do" "catch" "return"))

(defun piccode-font-lock-keywords ()
  (list
   `(,(regexp-opt (piccode-keywords) 'symbols) . font-lock-keyword-face)
   `(,(regexp-opt (piccode-constants) 'symbols) . font-lock-type-face)))

(defun piccode--previous-non-empty-line ()
  (save-excursion
    (forward-line -1)
    (while (and (not (bobp))
                (string-empty-p
                 (string-trim-right
                  (thing-at-point 'line t))))
      (forward-line -1))
    (thing-at-point 'line t)))

(defun piccode--indentation-of-previous-non-empty-line ()
  (save-excursion
    (forward-line -1)
    (while (and (not (bobp))
                (string-empty-p
                 (string-trim-right
                  (thing-at-point 'line t))))
      (forward-line -1))
    (current-indentation)))

(defun piccode--desired-indentation ()
  (let* ((cur-line (string-trim-right (thing-at-point 'line t)))
         (prev-line (string-trim-right (piccode--previous-non-empty-line)))
         (indent-len 4)
         (prev-indent (piccode--indentation-of-previous-non-empty-line)))
    (cond
     ((string-match-p "^\\s-*when\\s-*(.+)" prev-line)
      prev-indent)
     ((and (string-suffix-p "{" prev-line)
           (string-prefix-p "}" (string-trim-left cur-line)))
      prev-indent)
     ((string-suffix-p "{" prev-line)
      (+ prev-indent indent-len))
     ((string-prefix-p "}" (string-trim-left cur-line))
      (max (- prev-indent indent-len) 0))
     ((string-suffix-p "->" prev-line)
      (if (string-suffix-p "->" cur-line)
          prev-indent
        (+ prev-indent indent-len)))
     ((string-suffix-p "->" cur-line)
      (max (- prev-indent indent-len) 0))
     (t prev-indent))))

;;; TODO: customizable indentation (amount of spaces, tabs, etc)
(defun piccode-indent-line ()
  (interactive)
  (when (not (bobp))
    (let* ((desired-indentation
            (piccode--desired-indentation))
           (n (max (- (current-column) (current-indentation)) 0)))
      (indent-line-to desired-indentation)
      (forward-char n))))

(define-derived-mode piccode-mode prog-mode "PiccodeScript"
  "PiccodeScript mode for emacs"
  :syntax-table piccode-mode-syntax-table
  (setq-local font-lock-defaults '(piccode-font-lock-keywords))
  (setq-local indent-line-function 'piccode-indent-line)
  (setq-local comment-start "// "))

(provide 'piccode-mode)
