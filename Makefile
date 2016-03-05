.PHONY: vim tags
vim : 
	zsh -c "vim ./src/**/*.java"
tags : 
	zsh -c "ctags -R ./**/*.c(N) ./**/*.py(N) ./**/*.html(N) ./**/*.js(N) ./**/*.java(N)"
