usage:
	@echo "Usage:"
	@echo "  make # this help (try ./gradlew tasks and follow"
	@echo "       # instructions for building some artifacts)"
	@echo "  make build # runs ./gradlew build"
	@echo "  make pull # to pull git flow branches"
	@echo "  make push # to push git flow branches"

build:
	./gradlew build

BRANCH := $(shell git branch | sed -n -e 's/^\* \(.*\)/\1/p')

pull:	
	git fetch --prune
	git checkout master && git merge origin/master
	git checkout develop && git merge origin/develop
	git checkout ${BRANCH} && git merge origin/${BRANCH}

push: pull
	git push --follow-tags origin master develop

clean:
	rm -rf build .gradle

.PHONY: usage build pull push clean
