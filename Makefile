.PHONY: vim tags
tags : 
	zsh -c "ctags -R ./**/*.c(N) ./**/*.py(N) ./**/*.html(N) ./**/*.js(N) ./**/*.java(N)"
vim : 
	zsh -c "vim ./src/**/*.java"
